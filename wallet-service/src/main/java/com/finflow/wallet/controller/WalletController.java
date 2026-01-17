package com.finflow.wallet.controller;

import com.finflow.wallet.dto.AddMoneyRequest;
import com.finflow.wallet.dto.AddMoneyResponse;
import com.finflow.wallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.finflow.wallet.dto.TransferMoneyRequest;
import com.finflow.wallet.dto.TransferMoneyResponse;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/add-money")
    public ResponseEntity<AddMoneyResponse> addMoney(
            @Valid @RequestBody AddMoneyRequest request) {

        return ResponseEntity.ok(walletService.addMoney(request));
    }
    @PostMapping("/transfer")
    public ResponseEntity<TransferMoneyResponse> transferMoney(
            @Valid @RequestBody TransferMoneyRequest request) {

        return ResponseEntity.ok(walletService.transferMoney(request));
    }

}
