package com.service.impl;

import com.domain.User;
import com.domain.UserRole;
import com.exception.BusinessException;
import com.exception.ResourceNotFoundException;
import com.mapper.impl.UserMapper;
import com.model.bo.StatusCommon;
import com.model.dto.*;
import com.model.request.DetailUser;
import com.model.request.LoginVM;
import com.model.request.ManagedUserVM;
import com.model.request.UserRequest;
import com.repository.UserRepository;
import com.repository.UserRoleRepository;
import com.security.AuthoritiesConstants;
import com.security.jwt.TokenProvider;
import com.service.UserService;
import com.util.ErrorCode;
import com.util.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserRoleRepository userRoleRepository;

    private final TokenProvider tokenProvider;

    private static boolean isPasswordLengthInvalid(String password) {
        return (StringUtils.isEmpty(password) || password.length() < ManagedUserVM.PASSWORD_MIN_LENGTH || password.length() > ManagedUserVM.PASSWORD_MAX_LENGTH);
    }

    @Override
    public LoginResponse authorize(LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken;
        if (!loginVM.getEmail().isEmpty()) {
            authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginVM.getEmail().toLowerCase(Locale.ENGLISH),
                    loginVM.getPassword()
            );
        } else {
            authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginVM.getUserName().toLowerCase(Locale.ENGLISH),
                    loginVM.getPassword()
            );
        }

        Set<String> roleList = new HashSet<>();
        User user;
        if (!loginVM.getEmail().isEmpty()) {
            user = userRepository.findByEmailAndStatusNot(loginVM.getEmail().toLowerCase(Locale.ENGLISH), StatusCommon.DELETE).orElse(null);
        } else {
            user = userRepository.findByUserNameAndStatusNot(loginVM.getUserName().toLowerCase(Locale.ENGLISH), StatusCommon.DELETE).orElse(null);
        }
        if (user == null)
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, ErrorMessage.USER_NOT_FOUND);
        boolean validatePassword = passwordEncoder.matches(loginVM.getPassword(), user.getPassword());
        if (!validatePassword)
            throw new BusinessException(ErrorCode.PASSWORD_INVALID, ErrorMessage.PASS_WORD_INVALID);
        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
        if (!CollectionUtils.isEmpty(userRoles))
            roleList = userRoles.stream().map(UserRole::getRoleName).collect(Collectors.toSet());
        AccountAuth accountAuth = userMapper.mapAccountAuth(user);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.findOrGenerateJwt(authentication, loginVM.isRememberMe(), null);

        return new LoginResponse(accountAuth, jwt, roleList);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserResponse registerUser(UserRequest userRequest) {

        if (isPasswordLengthInvalid(userRequest.getPassword()) || !Objects.equals(userRequest.getPassword(), userRequest.getConfirmPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD_TYPE, ErrorMessage.INVALID_PASSWORD_TYPE);
        }
        User newUser = userRepository.findByEmailAndStatusNot(userRequest.getEmail(), StatusCommon.DELETE).orElse(null);
        if (newUser != null)
            throw new BusinessException(ErrorCode.EMAIL_ALREADY, ErrorMessage.EMAIL_ALREADY);
        else newUser = new User();
        newUser = userMapper.mapToEntity(newUser, userRequest);
        String encryptedPassword = passwordEncoder.encode(userRequest.getPassword());
        newUser.setPassword(encryptedPassword);
        newUser.setFullName(userRequest.getName());
        newUser.setUserName(userRequest.getUserName());
        userRepository.insert(newUser);
        String userId = newUser.getId();
        UserRole userRoleList = mapToEntityUserRole(userId);
        userRoleRepository.insert(userRoleList);
        Set<String> roleName = Set.of(AuthoritiesConstants.USER);
        UserResponse userResponse = userMapper.mapToResponse(newUser);
        userResponse.setRoleNames(roleName);

        return userResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changeRole(String tokenRequest, String type) {
        String token = null;
        if (org.springframework.util.StringUtils.hasText(tokenRequest) && tokenRequest.startsWith("Bearer "))
            token = tokenRequest.substring(7);
        DetailUser detailUser = tokenProvider.getClaim(token);
        User user = userRepository.getById(detailUser.getUserId());
        UserRole userRole = userRoleRepository.findByUserIdAndRoleName(user.getId(), type);
        if (userRole != null)
            throw new BusinessException(ErrorCode.ROLE_IS_ALREADY, ErrorMessage.ROLE_IS_ALREADY);
        user.setStatus(StatusCommon.WAITING);
        user.setRole(type);
        userRepository.update(user);
        return true;
    }

    @Override
    public Boolean approve(String userId, boolean approve) {
        User user = userRepository.getById(userId);
        if (approve) {
            List<UserRole> userRole = userRoleRepository.findByUserId(userId);
            userRole = userRole.stream().map(it -> it.setRoleName(user.getRole())).collect(Collectors.toList());
            userRoleRepository.updateAll(userRole);
            user.setStatus(StatusCommon.ACTIVE);
            userRepository.update(user);
        } else {
            user.setRole(null);
            user.setStatus(StatusCommon.ACTIVE);
            userRepository.update(user);
        }
        return true;
    }

    @Override
    public Page<UserResponse> getUserWaitingApprove(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<User> userList;
        if (StringUtils.isEmpty(name))
            userList = userRepository.findByStatus(StatusCommon.WAITING);
        else {
            name = "%" + name.toLowerCase().trim() + "%";
            userList = userRepository.findByFullNameOrUserName(name);
        }
        if (CollectionUtils.isEmpty(userList))
            return createPageFromList(new ArrayList<>(), pageable);
        List<UserResponse> userResponses = userMapper.mapToListUserResponse(userList);
        return createPageFromList(userResponses, pageable);
    }

    @Override
    @Transactional
    public UserUpdateInfoDTO updateUserInformation(UserUpdateInfoDTO updateInfoDTO) {
        User user = userRepository.findById(tokenProvider.getCurrentUserId()).orElseThrow(()
                -> new BusinessException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));
        user.setFullName(!StringUtils.isEmpty(updateInfoDTO.getFullName()) ? updateInfoDTO.getFullName() : user.getFullName());
        user.setImageUrl(!StringUtils.isEmpty(updateInfoDTO.getImageKey()) ? updateInfoDTO.getImageKey() : user.getImageUrl());
        user.setEmail(!StringUtils.isEmpty(updateInfoDTO.getEmail()) ? updateInfoDTO.getEmail() : user.getEmail());
        user.setAge(updateInfoDTO.getAge() != null ? updateInfoDTO.getAge() : user.getAge());
        user.setAddress(!StringUtils.isEmpty(updateInfoDTO.getAddress()) ? updateInfoDTO.getAddress() : user.getAddress());
        user.setIdentity(updateInfoDTO.getIdentity() != null ? updateInfoDTO.getIdentity() : user.getIdentity());
        user.setGender(!Objects.isNull(updateInfoDTO.getGender()) ? updateInfoDTO.getGender() : user.getGender());
        user.setPhone(updateInfoDTO.getPhone() != null ? updateInfoDTO.getPhone() : user.getPhone());
        user.setBirthDay(updateInfoDTO.getBirthDay() != null ? updateInfoDTO.getBirthDay() : user.getBirthDay());
        userRepository.update(user);
        return userMapper.mapToResponseUser(user);
    }

    @Override
    public UserResponse getUserDetail(String userId) {
        User user = userRepository.getById(userId);
        List<UserRole> userRole = userRoleRepository.findByUserId(userId);
        Set<String> roleName = userRole.stream().map(UserRole::getRoleName).collect(Collectors.toSet());
        UserResponse userResponse = userMapper.mapToResponse(user);
        userResponse.setRoleNames(roleName);
        return userResponse;
    }

    @Override
    public Page<UserResponse> findAll(String name,String searchText, Integer page, Integer size) {
        User checkUser = userRepository.findById(tokenProvider.getCurrentUserId()).orElseThrow(()
                -> new BusinessException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));
        Pageable pageable = PageRequest.of(page, size);
        List<UserRole> userRoles = new ArrayList<>();
        List<UserResponse> userResponses;
        List<User> user;
        if (searchText.equals(AuthoritiesConstants.USER) && checkUser.getRole().equals(AuthoritiesConstants.ADMIN))
            userRoles = userRoleRepository.findByRoleName(AuthoritiesConstants.USER);
        if (searchText.equals(AuthoritiesConstants.SUPPORTER))
            userRoles = userRoleRepository.findByRoleName(AuthoritiesConstants.SUPPORTER);
        if (StringUtils.isEmpty(searchText) && checkUser.getRole().equals(AuthoritiesConstants.ADMIN))
            userRoles = userRoleRepository.findByRoleNameIn(Set.of(AuthoritiesConstants.USER, AuthoritiesConstants.SUPPORTER));
        if (CollectionUtils.isEmpty(userRoles))
            throw new BusinessException(ErrorCode.ROLE_NOT_FOUND, ErrorMessage.ROLE_NOT_FOUND);
        Map<String, List<UserRole>> userRoleMap = userRoles.stream().collect(groupingBy(UserRole::getUserId));
        Set<String> userIds = userRoles.stream().map(UserRole::getUserId).collect(Collectors.toSet());
        if (StringUtils.isEmpty(name))
            user = userRepository.findByIdInAndStatusNot(userIds, StatusCommon.DELETE);
        else {
            name = "%" + name.toLowerCase().trim() + "%";
            user = userRepository.findByIdInAndStatusNotByName(name,userIds, StatusCommon.DELETE);
        }
        if (CollectionUtils.isEmpty(user))
            user = new ArrayList<>();
        userResponses = userMapper.mapToListUserResponse(user, userRoleMap);
        return createPageFromList(userResponses, pageable);
    }

    public <T> Page<T> createPageFromList(List<T> list, Pageable pageable) {
        int startOfPage = pageable.getPageNumber() * pageable.getPageSize();
        if (startOfPage > list.size() || CollectionUtils.isEmpty(list))
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        int endOfPage = Math.min(startOfPage + pageable.getPageSize(), list.size());
        return new PageImpl<>(list.subList(startOfPage, endOfPage), pageable, list.size());
    }

    private UserRole mapToEntityUserRole(String userId) {
        return new UserRole().setUserId(userId).setRoleName(AuthoritiesConstants.USER);
    }



}
