package com.agileactors.moneytransfer.dao;

import com.agileactors.moneytransfer.model.Account;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends CrudRepository<Account, Integer> {

  @Query("select * from account where account_id = :accountId")
  Optional<Account> findByAccountId(@Param("accountId") Integer accountId);

  @Query("select * from account where account_id in (:accountIds)")
  List<Account> findByAccountIds(@Param("accountIds") List<Integer> accountIds);

  @Modifying
  @Query("update account set balance = 0")
  void resetAccounts();
}
