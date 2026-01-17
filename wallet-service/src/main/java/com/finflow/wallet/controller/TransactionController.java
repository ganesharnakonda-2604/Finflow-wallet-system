package com.finflow.wallet.controller;

import com.finflow.wallet.dto.TransactionHistoryResponse;
import com.finflow.wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{userId}")
    public Page<TransactionHistoryResponse> getHistory(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return transactionService.getTransactionHistory(userId, page, size);
    }
}
