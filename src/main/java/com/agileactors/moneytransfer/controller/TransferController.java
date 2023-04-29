package com.agileactors.moneytransfer.controller;

import com.agileactors.moneytransfer.dto.ApiTransfer;
import com.agileactors.moneytransfer.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransferController {

  private final TransferService transferService;

  @Autowired
  public TransferController(TransferService transferService) {
    this.transferService = transferService;
  }

  @PostMapping("/transfer")
  public void getAccount(@Valid @RequestBody ApiTransfer apiTransfer) {
    transferService.transferMoney(
        apiTransfer.getSourceAccountId(),
        apiTransfer.getTargetAccountId(),
        apiTransfer.getAmount(),
        apiTransfer.getCurrency());
  }
}
