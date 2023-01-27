package com.service;

import com.model.request.DetailUser;

public interface UserInternalService {

    DetailUser getUserByLogin(String email);
}
