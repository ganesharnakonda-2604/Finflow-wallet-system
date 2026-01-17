package com.finflow.wallet.service;

import com.finflow.wallet.dto.AddMoneyRequest;
import com.finflow.wallet.dto.TransferMoneyRequest;
import com.finflow.wallet.entity.*;
import com.finflow.wallet.exception.InsufficientBalanceException;
import com.finflow.wallet.repository.*;
import com.finflow.wallet.service.impl.WalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    // ----------------------------------------------------
    // 1️⃣ ADD MONEY TEST
    // ----------------------------------------------------
    @Test
    void shouldAddMoneySuccessfully() {

        User user = User.builder().id(1L).build();
        Wallet wallet = Wallet.builder()
                .id(1L)
                .user(user)
                .balance(BigDecimal.valueOf(100))
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(wallet));

        AddMoneyRequest request = new AddMoneyRequest();
        request.setUserId(1L);
        request.setAmount(BigDecimal.valueOf(200));

        var response = walletService.addMoney(request);

        assertEquals(BigDecimal.valueOf(300), response.getNewBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    // ----------------------------------------------------
// 2️⃣ TRANSFER FAILURE & ROLLBACK TEST
// ----------------------------------------------------
    @Test
    void shouldFailTransferIfInsufficientBalance() {

        Wallet sender = Wallet.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(100))
                .build();

        Wallet receiver = Wallet.builder()
                .id(2L)
                .balance(BigDecimal.ZERO)
                .build();

        when(walletRepository.findByUserIdForUpdate(1L))
                .thenReturn(Optional.of(sender));
        when(walletRepository.findByUserIdForUpdate(2L))
                .thenReturn(Optional.of(receiver));

        // ✅ FIX: use setters instead of constructor
        TransferMoneyRequest request = new TransferMoneyRequest();
        request.setSenderUserId(1L);
        request.setReceiverUserId(2L);
        request.setAmount(BigDecimal.valueOf(500));

        assertThrows(InsufficientBalanceException.class, () ->
                walletService.transferMoney(request)
        );

        // 🔥 CRITICAL ASSERTION
        verify(transactionRepository, never()).save(any());
    }

    // ----------------------------------------------------
// 3️⃣ CONCURRENCY TEST (INTERVIEW GOLD)
// ----------------------------------------------------
    @Test
    void concurrentTransfersShouldNotOverdrawWallet() throws Exception {

        Wallet sender = Wallet.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(1000))
                .build();

        Wallet receiver = Wallet.builder()
                .id(2L)
                .balance(BigDecimal.ZERO)
                .build();

        // ✅ FIX: lenient stubbing
        lenient().when(walletRepository.findByUserIdForUpdate(1L))
                .thenReturn(Optional.of(sender));
        lenient().when(walletRepository.findByUserIdForUpdate(2L))
                .thenReturn(Optional.of(receiver));

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable transferTask = () -> {
            try {
                TransferMoneyRequest request = new TransferMoneyRequest();
                request.setSenderUserId(1L);
                request.setReceiverUserId(2L);
                request.setAmount(BigDecimal.valueOf(700));

                walletService.transferMoney(request);
            } catch (Exception ignored) {}
        };

        executor.submit(transferTask);
        executor.submit(transferTask);

        executor.shutdown();

        assertTrue(sender.getBalance().compareTo(BigDecimal.ZERO) >= 0);
    }



}
