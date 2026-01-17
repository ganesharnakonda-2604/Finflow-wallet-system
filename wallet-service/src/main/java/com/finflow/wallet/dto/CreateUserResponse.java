package com.finflow.wallet.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserResponse {

    private Long userId;
    private Long walletId;
    private String message;
}
