package com.agileactors.moneytransfer.dao;

import com.agileactors.moneytransfer.model.Transfer;
import org.springframework.data.repository.CrudRepository;

public interface TransferRepository extends CrudRepository<Transfer, Integer> {}
