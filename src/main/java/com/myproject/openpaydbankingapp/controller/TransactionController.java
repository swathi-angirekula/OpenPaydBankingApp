package com.myproject.openpaydbankingapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.openpaydbankingapp.Exception.CustomParentException;
import com.myproject.openpaydbankingapp.Exception.TransactionException;
import com.myproject.openpaydbankingapp.model.Transaction;
import com.myproject.openpaydbankingapp.repository.AccountRepository;
import com.myproject.openpaydbankingapp.repository.TransactionRepository;

/**
 * 
 * This application is responsible to create new transaction and retrieves all
 * transaction details.
 * 
 * @author Swathi Angirekula
 *
 */
@RestController
public class TransactionController {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountController accountController;

	/**
	 * This method is responsible to retrieve all transaction details.
	 * 
	 * @return list of transactions.
	 */
	@GetMapping("/transactions")
	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}

	/**
	 * 
	 * This method is responsible to create new transaction, by adding and removing
	 * amount from different accounts
	 * 
	 * @param transaction
	 * @return transaction
	 * @throws CustomParentException, If customer information is invalid.
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = TransactionException.class)
	@PostMapping("/transactions")
	public Transaction createTransaction(@Valid @RequestBody Transaction transaction) throws CustomParentException {

		Long debitAccId = transaction.getDebitAccountId();
		Long creditAccId = transaction.getCreditAccountId();
		Double amount = transaction.getAmount();

		accountController.addAmount(creditAccId, amount);
		accountController.removeAmount(debitAccId, amount);
		return transactionRepository.save(transaction);
	}

}
