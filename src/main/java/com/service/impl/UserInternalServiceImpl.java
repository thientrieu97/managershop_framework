package com.service.impl;

import com.domain.User;
import com.exception.BusinessException;
import com.mapper.impl.UserMapper;
import com.model.bo.StatusCommon;
import com.model.request.DetailUser;
import com.repository.UserRepository;
import com.service.UserInternalService;
import com.util.ErrorCode;
import com.util.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInternalServiceImpl implements UserInternalService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public DetailUser getUserByLogin(String email) {
        User user = userRepository.findByEmailAndStatusNot(email, StatusCommon.DELETE).orElse(null);
        if (user == null)
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, ErrorMessage.USER_NOT_FOUND);
        return userMapper.mapToUserDetail(user);
    }
}
