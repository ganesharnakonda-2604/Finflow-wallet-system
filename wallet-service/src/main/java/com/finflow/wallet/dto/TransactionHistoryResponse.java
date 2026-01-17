package com.finflow.wallet.dto;

import com.finflow.wallet.entity.TransactionStatus;
import com.finflow.wallet.entity.TransactionType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class TransactionHistoryResponse {

    private Long transactionId;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;
    private LocalDateTime createdAt;
}
