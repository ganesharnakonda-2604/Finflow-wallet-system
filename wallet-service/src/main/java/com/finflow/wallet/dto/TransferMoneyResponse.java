package com.finflow.wallet.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class TransferMoneyResponse {

    private Long senderWalletId;
    private Long receiverWalletId;
    private BigDecimal senderBalance;
    private BigDecimal receiverBalance;
    private String message;
}
