package com.service;

import com.model.dto.LoginResponse;
import com.model.dto.UserResponse;
import com.model.dto.UserUpdateInfoDTO;
import com.model.request.LoginVM;
import com.model.request.UserRequest;
import org.springframework.data.domain.Page;

public interface UserService {

    LoginResponse authorize(LoginVM loginVM);

    UserResponse registerUser(UserRequest userRequest);

    Boolean changeRole(String token, String type);

    Boolean approve(String userId, boolean approve);

    Page<UserResponse> getUserWaitingApprove(String name, Integer page, Integer size);

    UserUpdateInfoDTO updateUserInformation(UserUpdateInfoDTO updateInfoDTO);

    UserResponse getUserDetail(String userId);

    Page<UserResponse> findAll(String name, String type, Integer page, Integer size);

}
