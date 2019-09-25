package com.myproject.openpaydbankingapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.openpaydbankingapp.model.Account;
import com.myproject.openpaydbankingapp.model.Account.BalanceStatus;
import com.myproject.openpaydbankingapp.model.Account.Type;
import com.myproject.openpaydbankingapp.model.Address;
import com.myproject.openpaydbankingapp.model.Client;
import com.myproject.openpaydbankingapp.model.Transaction;
import com.myproject.openpaydbankingapp.repository.AccountRepository;
import com.myproject.openpaydbankingapp.repository.TransactionRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application.properties")
public class TransactionControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	/**
	 * Given the Accounts exist in DB , when transaction is initiated between two
	 * accounts , the same should be created in DB.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateTransaction() throws Exception {
		// Given
		Address address1 = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Address address2 = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Client client = Client.builder().name("Swathi").surname("Angirekula").primaryAddress(address1)
				.secondaryAddress(address1).build();
		Client client2 = Client.builder().name("Eliyaz").surname("Shaik").primaryAddress(address2)
				.secondaryAddress(address2).build();
		Account account1 = Account.builder().accountType(Type.SAVINGS).balance(Double.valueOf(1500))
				.balanceStatus(BalanceStatus.CR).client(client).build();

		Account account2 = Account.builder().accountType(Type.SAVINGS).balance(Double.valueOf(1500))
				.balanceStatus(BalanceStatus.DR).client(client2).build();

		accountRepository.save(account1);
		accountRepository.save(account2);

		List<Account> accounts = accountRepository.findAll();
		// When
		Long creditAccountId = accounts.get(0).getId();
		Long debitAccountId = accounts.get(1).getId();
		Transaction transaction = Transaction.builder().amount(Double.valueOf(200)).creditAccountId(creditAccountId)
				.debitAccountId(debitAccountId).message("Test transaction").build();
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(transaction);
		MvcResult result = mvc.perform(post("/transactions/").content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		List<Transaction> transactions = transactionRepository.findAll();
		// Then
		Assert.assertFalse(transactions.isEmpty());

		String responseContent = result.getResponse().getContentAsString();
		Assert.assertTrue(responseContent.contains(transaction.getAmount().toString()));
		Assert.assertTrue(responseContent.contains(transaction.getMessage().toString()));
	}

	/**
	 * Given the Accounts exist in DB , when multiple transactions are initiated
	 * between two accounts with CR and CR status, amount should be transferred and
	 * status should be updated
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateMultipleTransactions() throws Exception {
		// Given
		Address address1 = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Address address2 = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Client client = Client.builder().name("Swathi").surname("Angirekula").primaryAddress(address1)
				.secondaryAddress(address1).build();
		Client client2 = Client.builder().name("Eliyaz").surname("Shaik").primaryAddress(address2)
				.secondaryAddress(address2).build();
		Account fromAccount = Account.builder().accountType(Type.SAVINGS).balance(Double.valueOf(1500))
				.balanceStatus(BalanceStatus.CR).client(client).build();

		Account toAccount = Account.builder().accountType(Type.SAVINGS).balance(Double.valueOf(1500))
				.balanceStatus(BalanceStatus.CR).client(client2).build();

		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);

		List<Account> accounts = accountRepository.findAll();
		// When
		Long debitAccountId = accounts.get(0).getId();
		Long creditAccountId = accounts.get(1).getId();
		Transaction transaction = Transaction.builder().amount(Double.valueOf(200)).creditAccountId(creditAccountId)
				.debitAccountId(debitAccountId).message("Test transaction").build();
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(transaction);
		for (int i = 0; i < 10; i++) {
			MvcResult result = mvc
					.perform(post("/transactions/").content(content).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			String responseContent = result.getResponse().getContentAsString();
			Assert.assertTrue(responseContent.contains(transaction.getAmount().toString()));
			Assert.assertTrue(responseContent.contains(transaction.getMessage().toString()));
		}
		List<Transaction> transactions = transactionRepository.findAll();
		// Then
		Assert.assertFalse(transactions.isEmpty());
		Assert.assertTrue(transactions.size() == 10);

		accounts = accountRepository.findAll();
		Assert.assertEquals(String.valueOf(accounts.get(1).getBalance()), String.valueOf(new Double(3500)));
		Assert.assertEquals(accounts.get(1).getBalanceStatus(), BalanceStatus.CR);
		Assert.assertEquals(String.valueOf(accounts.get(0).getBalance()), String.valueOf(new Double(500)));
		Assert.assertEquals(accounts.get(0).getBalanceStatus(), BalanceStatus.DR);
	}

	/**
	 * Given the Accounts exist in DB , when transaction is initiated between two
	 * accounts with CR and DR status, amount should be transferred and status
	 * should be updated
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInitiateTransactionCR_DR() throws Exception {
		// Given
		Address address1 = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Address address2 = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Client client = Client.builder().name("Swathi").surname("Angirekula").primaryAddress(address1)
				.secondaryAddress(address1).build();
		Client client2 = Client.builder().name("Eliyaz").surname("Shaik").primaryAddress(address2)
				.secondaryAddress(address2).build();
		Account account1 = Account.builder().accountType(Type.SAVINGS).balance(Double.valueOf(1500))
				.balanceStatus(BalanceStatus.CR).client(client).build();

		Account account2 = Account.builder().accountType(Type.SAVINGS).balance(Double.valueOf(1500))
				.balanceStatus(BalanceStatus.DR).client(client2).build();

		accountRepository.save(account1);
		accountRepository.save(account2);

		List<Account> accounts = accountRepository.findAll();
		// When
		Long debitAccountId = accounts.get(0).getId();
		Long creditAccountId = accounts.get(1).getId();
		Transaction transaction = Transaction.builder().amount(Double.valueOf(2500)).creditAccountId(creditAccountId)
				.debitAccountId(debitAccountId).message("Test transaction").build();
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(transaction);
		mvc.perform(post("/transactions/").content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		accounts = accountRepository.findAll();
		// Then
		Assert.assertEquals(String.valueOf(accounts.get(1).getBalance()), String.valueOf(new Double(1000)));
		Assert.assertEquals(accounts.get(1).getBalanceStatus(), BalanceStatus.CR);

	}

	/**
	 * Given the Accounts exist in DB , when transaction is initiated between two
	 * accounts with DR and DR status, amount should be transferred and status
	 * should be updated
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInitiateTransactionDR_DR() throws Exception {
		// Given
		Address address1 = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Address address2 = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Client client = Client.builder().name("Swathi").surname("Angirekula").primaryAddress(address1)
				.secondaryAddress(address1).build();
		Client client2 = Client.builder().name("Eliyaz").surname("Shaik").primaryAddress(address2)
				.secondaryAddress(address2).build();
		Account account1 = Account.builder().accountType(Type.SAVINGS).balance(Double.valueOf(1500))
				.balanceStatus(BalanceStatus.DR).client(client).build();

		Account account2 = Account.builder().accountType(Type.SAVINGS).balance(Double.valueOf(1500))
				.balanceStatus(BalanceStatus.DR).client(client2).build();

		accountRepository.save(account1);
		accountRepository.save(account2);

		List<Account> accounts = accountRepository.findAll();
		// When
		Long fromAccountId = accounts.get(0).getId();
		Long toAccountId = accounts.get(1).getId();
		Transaction transaction = Transaction.builder().amount(Double.valueOf(100)).creditAccountId(toAccountId)
				.debitAccountId(fromAccountId).message("Test transaction").build();
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(transaction);
		mvc.perform(post("/transactions/").content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		accounts = accountRepository.findAll();
		// Then
		Assert.assertEquals(String.valueOf(accounts.get(1).getBalance()), String.valueOf(new Double(1400)));
		Assert.assertEquals(accounts.get(1).getBalanceStatus(), BalanceStatus.DR);

	}

	/**
	 * Given the Accounts exist in DB , when transaction is initiated between two
	 * accounts with CR and DR status, amount should be transferred and status
	 * should be updated
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInitiateTransactionCR_DRWithStatusChange() throws Exception {
		// Given
		Address address1 = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Address address2 = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Client client = Client.builder().name("Swathi").surname("Angirekula").primaryAddress(address1)
				.secondaryAddress(address1).build();
		Client client2 = Client.builder().name("Eliyaz").surname("Shaik").primaryAddress(address2)
				.secondaryAddress(address2).build();
		Account creditAccount = Account.builder().accountType(Type.SAVINGS).balance(Double.valueOf(1500))
				.balanceStatus(BalanceStatus.CR).client(client).build();

		Account debitAccount = Account.builder().accountType(Type.SAVINGS).balance(Double.valueOf(1500))
				.balanceStatus(BalanceStatus.DR).client(client2).build();

		accountRepository.save(debitAccount);
		accountRepository.save(creditAccount);

		List<Account> accounts = accountRepository.findAll();
		// When
		Long fromAccountId = accounts.get(1).getId();
		Long toAccountId = accounts.get(0).getId();
		Transaction transaction = Transaction.builder().amount(Double.valueOf(1600)).creditAccountId(toAccountId)
				.debitAccountId(fromAccountId).message("Test transaction").build();
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(transaction);
		mvc.perform(post("/transactions/").content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		accounts = accountRepository.findAll();
		// Then
		Assert.assertEquals(String.valueOf(accounts.get(0).getBalance()), String.valueOf(new Double(100)));
		Assert.assertEquals(String.valueOf(accounts.get(1).getBalance()), String.valueOf(new Double(100)));
		Assert.assertEquals(accounts.get(0).getBalanceStatus(), BalanceStatus.CR);
		Assert.assertEquals(accounts.get(1).getBalanceStatus(), BalanceStatus.DR);

	}

	/**
	 * Given Accounts exist, when transactions are created and they should be
	 * retrieved
	 * 
	 * @throws Exception
	 */
	@Test
	// This test is used to verify if the transaction is created in the DB.
	public void testGetAllTransactions() throws Exception {
		// Given
		Address address1 = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Address address2 = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Client client = Client.builder().name("Swathi").surname("Angirekula").primaryAddress(address1)
				.secondaryAddress(address1).build();
		Client client2 = Client.builder().name("Eliyaz").surname("Shaik").primaryAddress(address2)
				.secondaryAddress(address2).build();
		Account account1 = Account.builder().accountType(Type.SAVINGS).balance(Double.valueOf(1500))
				.balanceStatus(BalanceStatus.CR).client(client).build();

		Account account2 = Account.builder().accountType(Type.SAVINGS).balance(Double.valueOf(1500))
				.balanceStatus(BalanceStatus.CR).client(client2).build();

		accountRepository.save(account1);
		accountRepository.save(account2);

		List<Account> accounts = accountRepository.findAll();
		// When
		Long creditAccountId = accounts.get(0).getId();
		Long debitAccountId = accounts.get(1).getId();
		Transaction transaction = Transaction.builder().amount(Double.valueOf(200)).creditAccountId(creditAccountId)
				.debitAccountId(debitAccountId).message("Test transaction").build();

		transactionRepository.save(transaction);

		List<Transaction> transactions = transactionRepository.findAll();
		Assert.assertFalse(transactions.isEmpty());

		MvcResult result = mvc.perform(get("/transactions/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		// Then

		String responseContent = result.getResponse().getContentAsString();
		Assert.assertTrue(responseContent.contains(transaction.getAmount().toString()));
		Assert.assertTrue(responseContent.contains(transaction.getMessage().toString()));
	}
}
