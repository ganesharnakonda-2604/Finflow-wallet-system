package com.finflow.wallet.service;

import com.finflow.wallet.dto.TransactionHistoryResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {

    Page<TransactionHistoryResponse> getTransactionHistory(
            Long userId, int page, int size);
}
