package com.finflow.wallet.service.impl;

import com.finflow.wallet.dto.CreateUserRequest;
import com.finflow.wallet.dto.CreateUserResponse;
import com.finflow.wallet.entity.User;
import com.finflow.wallet.entity.Wallet;
import com.finflow.wallet.repository.UserRepository;
import com.finflow.wallet.repository.WalletRepository;
import com.finflow.wallet.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {

        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("User already exists");
                });

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();

        user = userRepository.save(user);

        Wallet wallet = Wallet.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .build();

        walletRepository.save(wallet);

        return CreateUserResponse.builder()
                .userId(user.getId())
                .walletId(wallet.getId())
                .message("User and wallet created successfully")
                .build();
    }
}
