package com.finflow.wallet.service;

import com.finflow.wallet.dto.AddMoneyRequest;
import com.finflow.wallet.dto.AddMoneyResponse;
import com.finflow.wallet.dto.TransferMoneyRequest;
import com.finflow.wallet.dto.TransferMoneyResponse;
public interface WalletService {

    AddMoneyResponse addMoney(AddMoneyRequest request);
    TransferMoneyResponse transferMoney(TransferMoneyRequest request);

}
