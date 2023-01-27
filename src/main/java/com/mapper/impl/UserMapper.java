package com.mapper.impl;

import com.domain.User;
import com.domain.UserRole;
import com.model.bo.StatusCommon;
import com.model.dto.AccountAuth;
import com.model.dto.UserDTO;
import com.model.dto.UserResponse;
import com.model.dto.UserUpdateInfoDTO;
import com.model.request.DetailUser;
import com.model.request.UserRequest;
import com.security.AuthoritiesConstants;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.jhipster.security.RandomUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapper {

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).collect(Collectors.toList());
    }

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user);
    }

    public DetailUser mapToUserDetail(User user) {
        return new DetailUser().setUserId(user.getId())
                .setEmail(user.getEmail())
                .setName(user.getFullName());
    }

    public User mapToEntity(User newUser, UserRequest userRequest) {

        newUser.setStatus(StatusCommon.ACTIVE);
        newUser.setRole(AuthoritiesConstants.USER);
        if (userRequest.getEmail() != null) {
            newUser.setEmail(userRequest.getEmail().toLowerCase());
        }
        newUser.setActivationKey(RandomUtil.generateActivationKey());

        return newUser;
    }

    public UserResponse mapToResponse(User newUser) {

        return new UserResponse().setId(newUser.getId())
                .setAge(newUser.getAge())
                .setEmail(newUser.getEmail())
                .setAddress(newUser.getAddress())
                .setImageKey(newUser.getImageUrl())
                .setIdentity(newUser.getIdentity())
                .setBankAccount(newUser.getTotalMoney())
                .setStatusCommon(newUser.getStatus())
                .setFullName(newUser.getFullName())
                .setBirthDay(newUser.getBirthDay())
                .setGender(newUser.getGender())
                .setUserName(newUser.getUserName())
                .setDescription(newUser.getDescription())
                .setFacebook(newUser.getFacebook())
                .setTwitter(newUser.getTwitter())
                .setPhone(newUser.getPhone());
    }

    public UserUpdateInfoDTO mapToResponseUser(User newUser) {
        return new UserUpdateInfoDTO()
                .setAge(newUser.getAge())
                .setEmail(newUser.getEmail())
                .setAddress(newUser.getAddress())
                .setIdentity(newUser.getIdentity())
                .setFullName(newUser.getFullName())
                .setImageKey(newUser.getImageUrl())
                .setBirthDay(newUser.getBirthDay())
                .setGender(newUser.getGender())
                .setDescription(newUser.getDescription())
                .setFacebook(newUser.getFacebook())
                .setTwitter(newUser.getTwitter())
                .setPhone(newUser.getPhone());
    }

    public UserResponse mapToResponse(User newUser, Map<String, List<UserRole>> userRoleMap) {

        List<UserRole> userRole = userRoleMap.get(newUser.getId());
        Set<String> roleNames = userRole.stream().map(UserRole::getRoleName).collect(Collectors.toSet());
        return new UserResponse().setId(newUser.getId())
                .setAge(newUser.getAge())
                .setEmail(newUser.getEmail())
                .setAddress(newUser.getAddress())
                .setIdentity(newUser.getIdentity())
                .setBankAccount(newUser.getTotalMoney())
                .setStatusCommon(newUser.getStatus())
                .setFullName(newUser.getFullName())
                .setRoleNames(roleNames)
                .setImageKey(newUser.getImageUrl())
                .setUserName(newUser.getUserName())
                .setDescription(newUser.getDescription())
                .setFacebook(newUser.getFacebook())
                .setTwitter(newUser.getTwitter())
                .setPhone(newUser.getPhone());
    }

    public AccountAuth mapAccountAuth(User user) {
        return new AccountAuth().setAccountId(user.getId()).setEmail(user.getEmail())
                .setStatus(user.getStatus()).setUsername(user.getFullName()).setPhone(user.getPhone());
    }

    public List<UserResponse> mapToListUserResponse(List<User> userList) {
        return userList.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<UserResponse> mapToListUserResponse(List<User> userList, Map<String, List<UserRole>> userRoleMap) {
        return userList.stream().map(it -> mapToResponse(it, userRoleMap)).collect(Collectors.toList());
    }

    public DetailUser mapToUser(Claims claims) {
        return new DetailUser().setEmail(claims.getSubject()).setUserId(claims.getId());
    }
}
