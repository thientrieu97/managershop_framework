package com.controller;

import com.model.dto.*;
import com.model.request.LoginVM;
import com.model.request.UserRequest;
import com.security.AuthoritiesConstants;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Response<?> findAll(@RequestParam(value = "type") String type,
                               @RequestParam(value = "name") String name,
                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                               @Min(value = 0, message = "4157")
                               @RequestParam(value = "size", defaultValue = "50") Integer size) {
        return Response.ofSucceeded(userService.findAll(name,type, page - 1, size));
    }

    @GetMapping("/waiting")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Response<?> getUserWaitingApprove(@RequestParam(value = "name") String name,
                                             @Min(value = 1, message = "4156")
                                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                                             @Min(value = 0, message = "4157")
                                             @RequestParam(value = "size", defaultValue = "50") Integer size) {
        return Response.ofSucceeded(userService.getUserWaitingApprove(name, page - 1, size));
    }

    @GetMapping("/{id}")
    public Response<UserResponse> getUserDetail(@PathVariable("id") String userId) {
        return Response.ofSucceeded(userService.getUserDetail(userId));
    }

    @PostMapping("/register")
    public Response<UserResponse> registerAccount(@Valid @RequestBody UserRequest userRequest) {
        return Response.ofSucceeded(userService.registerUser(userRequest));
    }

    @PostMapping("/authenticate")
    public Response<LoginResponse> authorize(@RequestBody LoginVM loginVM) {
        return Response.ofSucceeded(userService.authorize(loginVM));
    }

    @PostMapping("/request-role")
    public Response<Boolean> changeRole(@RequestHeader("Authorization") String token, @RequestParam("type") String type) {
        return Response.ofSucceeded(userService.changeRole(token, type));
    }

    @PostMapping("/approve")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Response<Boolean> approve(@RequestParam("user-id") String userId, @RequestParam(value = "approve", defaultValue = "true") boolean approve) {
        return Response.ofSucceeded(userService.approve(userId, approve));
    }

    @PutMapping("/update")
    public Response<UserUpdateInfoDTO> updateUserInformation(@Valid @RequestBody UserUpdateInfoDTO updateInfoDTO) {
        return Response.ofSucceeded(userService.updateUserInformation(updateInfoDTO));
    }

}
