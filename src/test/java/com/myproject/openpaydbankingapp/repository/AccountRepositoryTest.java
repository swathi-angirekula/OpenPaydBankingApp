package com.myproject.openpaydbankingapp.repository;

import static org.hamcrest.CoreMatchers.is;

import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.myproject.openpaydbankingapp.model.Account;
import com.myproject.openpaydbankingapp.model.Account.BalanceStatus;
import com.myproject.openpaydbankingapp.model.Account.Type;
import com.myproject.openpaydbankingapp.model.Address;
import com.myproject.openpaydbankingapp.model.Client;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

	// This test is used to verify if the account is saved in the DB.
	@Test
	public void testAccountSave() {

		// Given
		Address address = Address.builder().id(new Random().nextLong()).addressLine1("Line1").addressLine2("Line2")
				.city("London").country("UK").build();
		Client client = Client.builder().id(new Random().nextLong()).name("Swathi").surname("Angirekula")
				.primaryAddress(address).secondaryAddress(address).build();
		Account accountSaved = Account.builder().id(new Random().nextLong()).accountType(Type.SAVINGS).balance(100.00)
				.balanceStatus(BalanceStatus.CR).client(client).build();
		// When
		accountRepository.save(accountSaved);

		List<Account> retrievedAccountList = accountRepository.findAll();
		// Then
		Assert.assertThat(retrievedAccountList.isEmpty(), is(false));
		Assert.assertEquals(accountSaved.toString(), retrievedAccountList.get(0).toString());
	}

	// This test is used to verify if multiple accounts are saved in the DB.
	@Test
	public void testAccountSave_multipleAccounts() {

		// Given
		Address address = Address.builder().id(new Random().nextLong()).addressLine1("Line1").addressLine2("Line2")
				.city("London").country("UK").build();
		Client client = Client.builder().id(new Random().nextLong()).name("Swathi").surname("Angirekula")
				.primaryAddress(address).secondaryAddress(address).build();
		for (int i = 0; i < 5; i++) {
			Account account = Account.builder().id(new Random().nextLong()).accountType(Type.SAVINGS).balance(100.00)
					.balanceStatus(BalanceStatus.CR).client(client).build();
			// When
			accountRepository.save(account);
		}

		List<Account> retrievedAccountList = accountRepository.findAll();
		// Then
		Assert.assertEquals(retrievedAccountList.size(), 5);
	}

}
