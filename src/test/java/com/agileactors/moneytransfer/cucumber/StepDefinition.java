package com.agileactors.moneytransfer.cucumber;

import static org.junit.Assert.assertEquals;

import com.agileactors.moneytransfer.dao.AccountRepository;
import com.agileactors.moneytransfer.dto.ApiTransfer;
import com.agileactors.moneytransfer.model.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class StepDefinition {

  ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

  @Autowired AccountRepository accountRepository;
  @Autowired private TestRestTemplate restTemplate;

  private Account sourceAccount;
  private Account targetAccount;
  private ResponseEntity<Void> responseEntity;

  @Before
  public void setup() {
    // The test data are loaded from docker/data.sql
    sourceAccount = accountRepository.findByAccountId(1).get();
    targetAccount = accountRepository.findByAccountId(2).get();
  }

  @After
  public void cleanup() {
    accountRepository.resetAccounts();
  }

  @Given("source account has a balance equal to {string}")
  public void theSourceAccountHasABalanceEqualTo(String amount) {
    sourceAccount.setBalance(new BigDecimal(amount));
    accountRepository.save(sourceAccount);
  }

  @And("the target account has a balance equal to {string}")
  public void theTargetAccountHasABalanceEqualTo(String amount) {
    targetAccount.setBalance(new BigDecimal(amount));
    accountRepository.save(targetAccount);
  }

  @When("a transaction request for {string} {string} is received")
  public void transfer(String amount, String currency) throws JsonProcessingException {
    String url = "/transfer";
    ApiTransfer transfer =
        new ApiTransfer(
            sourceAccount.getAccountId(),
            targetAccount.getAccountId(),
            new BigDecimal(amount),
            currency);
    String json = ow.writeValueAsString(transfer);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    HttpEntity<String> request = new HttpEntity<>(json, headers);
    responseEntity = restTemplate.postForEntity(url, request, Void.class);
  }

  @When("a transaction request is received from the source account to itself")
  public void aTransactionRequestIsReceivedFromTheSourceAccountToItself()
      throws JsonProcessingException {
    String url = "/transfer";
    ApiTransfer transfer =
        new ApiTransfer(
            sourceAccount.getAccountId(),
            sourceAccount.getAccountId(),
            new BigDecimal("10"),
            "EUR");
    String json = ow.writeValueAsString(transfer);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    HttpEntity<String> request = new HttpEntity<>(json, headers);
    responseEntity = restTemplate.postForEntity(url, request, Void.class);
  }

  @When("a transaction request is received with a nonexistent target account")
  public void aTransactionRequestIsReceivedWithANonexistentTargetAccount()
      throws JsonProcessingException {
    String url = "/transfer";
    ApiTransfer transfer =
        new ApiTransfer(sourceAccount.getAccountId(), 3, new BigDecimal("10"), "EUR");
    String json = ow.writeValueAsString(transfer);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    HttpEntity<String> request = new HttpEntity<>(json, headers);
    responseEntity = restTemplate.postForEntity(url, request, Void.class);
  }

  @And("the client of the API should receive an error with http status of {int}")
  public void theClientOfTheAPIShouldReceiveAnError(Integer statusCode) {
    assertEquals(responseEntity.getStatusCode(), HttpStatus.valueOf(statusCode));
  }

  @Then("the balance of the source account should be equal to {string}")
  public void checkSourceBalance(String amount) {
    String url = "/account/1";
    ResponseEntity<Account> account = restTemplate.getForEntity(url, Account.class);
    assertEquals(new BigDecimal(amount), account.getBody().getBalance());
  }

  @Then("the balance of the target account should be equal to {string}")
  public void checkTargetBalance(String amount) {
    String url = "/account/2";
    ResponseEntity<Account> account = restTemplate.getForEntity(url, Account.class);
    assertEquals(new BigDecimal(amount), account.getBody().getBalance());
  }
}
