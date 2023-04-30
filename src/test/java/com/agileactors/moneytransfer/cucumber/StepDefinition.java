package com.agileactors.moneytransfer.cucumber;

import static org.junit.Assert.assertEquals;

import com.agileactors.moneytransfer.dao.AccountRepository;
import com.agileactors.moneytransfer.dto.ApiDeposit;
import com.agileactors.moneytransfer.model.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class StepDefinition {

  ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

  @Autowired AccountRepository accountRepository;
  @Autowired private TestRestTemplate restTemplate;

  @Given("account with id {int} exists with currency {string}")
  public void accountId(Integer accountId, String currency) {
    accountRepository.save(new Account(new BigDecimal(0), currency));
  }

  @And("account with id {int} has a balance equal to {string} of currency {string}")
  public void deposit(Integer accountId, String amount, String currency)
      throws JsonProcessingException {
    String url = "/deposit";

    ApiDeposit apiDeposit = new ApiDeposit();
    apiDeposit.setAccountId(accountId);
    apiDeposit.setAmount(new BigDecimal(amount));
    apiDeposit.setCurrency(currency);

    String payload = ow.writeValueAsString(apiDeposit);

    // Create a new HttpEntity with the JSON payload and headers
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<>(payload, headers);

    // Send the POST request to the specified URL with the HttpEntity
    restTemplate.postForObject(url, entity, Void.class);
  }

  @Then(
      "the balance of source account with id {int} should be equal to {string} of currency {string}")
  public void check(Integer id, String amount, String currency) throws JsonProcessingException {

    String url = "/account/" + id;
    ResponseEntity<Account> account = restTemplate.getForEntity(url, Account.class);
    assertEquals(new BigDecimal(amount), account.getBody().getBalance());
  }
}
