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

import com.myproject.openpaydbankingapp.model.Transaction;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TransactionRepositoryTest {

	@Autowired
	private TransactionRepository transactionRepository;

	// This test is used to verify if the transaction is saved in the DB.
	@Test
	public void testTransactionSave() {

		// Given
		Transaction transaction = Transaction.builder().id(new Random().nextLong())
				.creditAccountId(new Random().nextLong()).debitAccountId(new Random().nextLong())
				.amount(new Random().nextDouble()).message("Transaction Created").build();
		// When
		transactionRepository.save(transaction);

		List<Transaction> retrievedTransactionList = transactionRepository.findAll();
		// Then
		Assert.assertThat(retrievedTransactionList.isEmpty(), is(false));
		Assert.assertEquals(transaction.toString(), retrievedTransactionList.get(0).toString());
	}

	// This test is used to verify if the multiple transactions are is saved in the
	// DB.
	@Test
	public void testTransactionSave_multipleTransactions() {

		// Given
		for (int i = 0; i < 5; i++) {
			Transaction transaction = Transaction.builder().id(new Random().nextLong())
					.creditAccountId(new Random().nextLong()).debitAccountId(new Random().nextLong())
					.amount(new Random().nextDouble()).message("Transaction 1 Created").build();
			// When
			transactionRepository.save(transaction);
		}
		List<Transaction> retrievedTransactionList = transactionRepository.findAll();
		// Then
		Assert.assertEquals(retrievedTransactionList.size(), 5);
	}
}
