package com.finflow.wallet.service.impl;

import com.finflow.wallet.dto.TransactionHistoryResponse;
import com.finflow.wallet.entity.Transaction;
import com.finflow.wallet.repository.TransactionRepository;
import com.finflow.wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public Page<TransactionHistoryResponse> getTransactionHistory(
            Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(
                page, size, Sort.by("createdAt").descending()
        );

        Page<Transaction> transactions =
                transactionRepository.findByWalletUserId(userId, pageable);

        return transactions.map(txn ->
                TransactionHistoryResponse.builder()
                        .transactionId(txn.getId())
                        .amount(txn.getAmount())
                        .type(txn.getType())
                        .status(txn.getStatus())
                        .createdAt(txn.getCreatedAt())
                        .build()
        );
    }
}
