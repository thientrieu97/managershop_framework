package com.security;

import com.domain.User;
import com.domain.UserRole;
import com.exception.BusinessException;
import com.model.bo.StatusCommon;
import com.repository.UserRepository;
import com.service.UserRoleServiceInternal;
import com.util.ErrorCode;
import com.util.ErrorMessage;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    private final UserRoleServiceInternal internalService;

    public DomainUserDetailsService(UserRepository userRepository, UserRoleServiceInternal internalService) {
        this.userRepository = userRepository;
        this.internalService = internalService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        if (!new EmailValidator().isValid(email, null))
            throw new BusinessException(ErrorCode.EMAIL_IS_INVALID, ErrorCode.EMAIL_IS_INVALID.getMessage());
        return userRepository.findByEmailAndStatusNot(email, StatusCommon.DELETE).map(user -> createSpringSecurityUser(email, user)).orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " was not found in the database"));
    }

    private Set<String> getRole(User user) {
        List<UserRole> userRoleList = internalService.findByUserID(user.getId());
        if (CollectionUtils.isEmpty(userRoleList))
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, ErrorMessage.USER_NOT_FOUND);
        return userRoleList.stream().map(UserRole::getRoleName).collect(Collectors.toSet());
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
        if (user.getStatus().equals(StatusCommon.DELETE)) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        Set<String> roleSet = getRole(user);
        List<GrantedAuthority> grantedAuthorities = roleSet.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
