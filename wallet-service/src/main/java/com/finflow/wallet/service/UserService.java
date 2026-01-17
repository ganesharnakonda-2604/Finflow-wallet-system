package com.finflow.wallet.service;

import com.finflow.wallet.dto.CreateUserRequest;
import com.finflow.wallet.dto.CreateUserResponse;

public interface UserService {

    CreateUserResponse createUser(CreateUserRequest request);
}
