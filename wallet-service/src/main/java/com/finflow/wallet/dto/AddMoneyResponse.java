package com.finflow.wallet.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class AddMoneyResponse {

    private Long walletId;
    private BigDecimal newBalance;
    private String message;
}
