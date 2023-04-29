package com.agileactors.moneytransfer.dao;

import com.agileactors.moneytransfer.model.Account;
import com.agileactors.moneytransfer.model.Transfer;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TransferRepository extends CrudRepository<Transfer, Integer> {

  @Query("select * from transfer where transferId = :transferId")
  Optional<Account> findIdByTransferId(@Param("transferId") String transferId);
}
