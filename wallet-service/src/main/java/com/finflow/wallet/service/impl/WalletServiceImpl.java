package com.finflow.wallet.service.impl;

import com.finflow.wallet.dto.AddMoneyRequest;
import com.finflow.wallet.dto.AddMoneyResponse;
import com.finflow.wallet.entity.*;
import com.finflow.wallet.repository.TransactionRepository;
import com.finflow.wallet.repository.UserRepository;
import com.finflow.wallet.repository.WalletRepository;
import com.finflow.wallet.service.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.finflow.wallet.dto.TransferMoneyResponse;
import com.finflow.wallet.dto.TransferMoneyRequest;
import com.finflow.wallet.exception.InsufficientBalanceException;
import com.finflow.wallet.exception.InvalidTransferException;
import com.finflow.wallet.exception.WalletNotFoundException;


import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public AddMoneyResponse addMoney(AddMoneyRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        // 1️⃣ Update balance
        BigDecimal updatedBalance = wallet.getBalance().add(request.getAmount());
        wallet.setBalance(updatedBalance);
        walletRepository.save(wallet);

        // 2️⃣ Create transaction record
        Transaction transaction = Transaction.builder()
                .wallet(wallet)
                .amount(request.getAmount())
                .type(TransactionType.CREDIT)
                .status(TransactionStatus.SUCCESS)
                .build();

        transactionRepository.save(transaction);

        return AddMoneyResponse.builder()
                .walletId(wallet.getId())
                .newBalance(updatedBalance)
                .message("Money added successfully")
                .build();
    }
    @Override
    @Transactional
    public TransferMoneyResponse transferMoney(TransferMoneyRequest request) {

        BigDecimal amount = request.getAmount(); // ✅ FIX

        if (request.getSenderUserId().equals(request.getReceiverUserId())) {
            throw new InvalidTransferException("Sender and receiver cannot be same");
        }

        Wallet senderWallet = walletRepository
                .findByUserIdForUpdate(request.getSenderUserId())
                .orElseThrow(() -> new WalletNotFoundException("Sender wallet not found"));

        Wallet receiverWallet = walletRepository
                .findByUserIdForUpdate(request.getReceiverUserId())
                .orElseThrow(() -> new WalletNotFoundException("Receiver wallet not found"));

        if (senderWallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        // Debit sender
        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));

        // Credit receiver
        receiverWallet.setBalance(receiverWallet.getBalance().add(amount));

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        Transaction debitTxn = Transaction.builder()
                .wallet(senderWallet)
                .amount(amount)
                .type(TransactionType.DEBIT)
                .status(TransactionStatus.SUCCESS)
                .build();

        Transaction creditTxn = Transaction.builder()
                .wallet(receiverWallet)
                .amount(amount)
                .type(TransactionType.CREDIT)
                .status(TransactionStatus.SUCCESS)
                .build();

        transactionRepository.save(debitTxn);
        transactionRepository.save(creditTxn);

        return TransferMoneyResponse.builder()
                .senderWalletId(senderWallet.getId())
                .receiverWalletId(receiverWallet.getId())
                .senderBalance(senderWallet.getBalance())
                .receiverBalance(receiverWallet.getBalance())
                .message("Transfer successful")
                .build();
    }


}
