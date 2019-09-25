package com.myproject.openpaydbankingapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.openpaydbankingapp.model.Account;
import com.myproject.openpaydbankingapp.model.Account.BalanceStatus;
import com.myproject.openpaydbankingapp.model.Account.Type;
import com.myproject.openpaydbankingapp.model.Address;
import com.myproject.openpaydbankingapp.model.Client;
import com.myproject.openpaydbankingapp.repository.AccountRepository;
import com.myproject.openpaydbankingapp.repository.ClientRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application.properties")
public class AccountControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Test
	public void testGetAllAccountsForClient() throws Exception {
		// Given
		Address address = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Client client = Client.builder().name("Eliyaz").surname("Shaik").primaryAddress(address)
				.secondaryAddress(address).build();
		Double balance = Math.abs(new Random().nextDouble());
		Account accountSaved = Account.builder().accountType(Type.SAVINGS).balance(balance)
				.balanceStatus(BalanceStatus.CR).client(client).build();

		accountRepository.save(accountSaved);
		List<Client> clients = clientRepository.findAll();
		Long clientId = clients.get(0).getId();
		MvcResult result = mvc
				.perform(get("/clients/" + clientId + "/accounts").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		Assert.assertTrue(content.contains(address.getAddressLine1()));
		Assert.assertTrue(content.contains(accountSaved.getBalance().toString()));
		Assert.assertTrue(content.contains(client.getName()));

	}

	@Test
	public void testCreateAccountForClient() throws Exception {
		// Given
		Address address = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Client client = Client.builder().name("Eliyaz").surname("Shaik").primaryAddress(address)
				.secondaryAddress(address).build();
		Double balance = Math.abs(new Random().nextDouble());
		Account accountSaved = Account.builder().accountType(Type.SAVINGS).balance(balance)
				.balanceStatus(BalanceStatus.CR).client(client).build();
		// When
		accountRepository.save(accountSaved);
		List<Client> clients = clientRepository.findAll();
		Long clientId = clients.get(0).getId();
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(accountSaved);
		// Then
		mvc.perform(post("/clients/" + clientId + "/accounts").content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		MvcResult result = mvc
				.perform(get("/clients/" + clientId + "/accounts").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String responseContent = result.getResponse().getContentAsString();
		Assert.assertTrue(responseContent.contains(address.getAddressLine1()));
		Assert.assertTrue(responseContent.contains(accountSaved.getBalance().toString()));
		Assert.assertTrue(responseContent.contains(client.getName()));

	}

	@Test
	public void testDepositMoneyIntoAccount() throws Exception {
		// Given
		Address address = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Client client = Client.builder().name("Eliyaz").surname("Shaik").primaryAddress(address)
				.secondaryAddress(address).build();
		Double balance = Math.abs(new Random().nextDouble());
		Account accountSaved = Account.builder().accountType(Type.SAVINGS).balance(balance)
				.balanceStatus(BalanceStatus.CR).client(client).build();
		// When
		accountRepository.save(accountSaved);
		List<Client> clients = clientRepository.findAll();
		Long clientId = clients.get(0).getId();
		List<Account> accounts = accountRepository.findAll();
		Long accId = accounts.get(0).getId();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		Double amount = Math.abs(new Random().nextDouble());
		params.add("accountId", accId.toString());
		params.add("amount", amount.toString());

		Double newBalance = balance + amount;

		// Then
		mvc.perform(put("/clients/" + clientId + "/accounts").params(params).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		MvcResult result = mvc
				.perform(get("/clients/" + clientId + "/accounts").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String responseContent = result.getResponse().getContentAsString();
		Assert.assertTrue(responseContent.contains(address.getAddressLine1()));
		Assert.assertTrue(responseContent.contains(newBalance.toString()));
		Assert.assertTrue(responseContent.contains(client.getName()));
	}

}
