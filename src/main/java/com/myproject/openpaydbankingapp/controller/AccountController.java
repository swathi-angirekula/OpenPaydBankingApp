package com.myproject.openpaydbankingapp.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.openpaydbankingapp.Exception.AccountException;
import com.myproject.openpaydbankingapp.Exception.ClientException;
import com.myproject.openpaydbankingapp.Exception.CustomParentException;
import com.myproject.openpaydbankingapp.Exception.TransactionException;
import com.myproject.openpaydbankingapp.model.Account;
import com.myproject.openpaydbankingapp.model.Account.BalanceStatus;
import com.myproject.openpaydbankingapp.model.Client;
import com.myproject.openpaydbankingapp.repository.AccountRepository;
import com.myproject.openpaydbankingapp.repository.ClientRepository;

/**
 * /**
 * 
 * This application is responsible for creating new bank account, deposit amount
 * to an account and retrieves account details based on client ID.
 *
 * @author Swathi Angirekula
 *
 */
@RestController
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ClientRepository clientRepository;

	/**
	 * 
	 * This method is responsible for creating new account for provided client ID.
	 * 
	 * @param account
	 * @param clientId
	 * @return
	 * @throws CustomParentException if client ID is Invalid
	 */
	@PostMapping("/clients/{clientId}/accounts")
	public Account createAccount(@Valid @RequestBody Account account, @PathVariable Long clientId)
			throws CustomParentException {
		Optional<Client> client = clientRepository.findById(clientId);
		if (!client.isPresent()) {
			throw new ClientException("Invalid Client ID in Path");
		}
		account.setClient(client.get());
		if (account.getBalance() < 0) {
			account.setBalanceStatus(BalanceStatus.DR);
			Double inputPositiveBalance = Math.abs(account.getBalance());
			account.setBalance(inputPositiveBalance);
		} else {
			account.setBalanceStatus(BalanceStatus.CR);
		}
		return accountRepository.save(account);
	}

	/**
	 * This method retrieves all account details for provided client ID.
	 * 
	 * @param clientId
	 * @return
	 * @throws CustomParentException if client ID is Invalid
	 */
	@GetMapping("/clients/{clientId}/accounts")
	public List<Account> getAllAccountsForClient(@PathVariable Long clientId) throws CustomParentException {
		Optional<Client> client = clientRepository.findById(clientId);
		if (!client.isPresent()) {
			throw new ClientException("Invalid Client ID in Path");
		}

		return accountRepository.findAllAccountsByClient(client.get());
	}

	/**
	 * This method is responsible to deposit money to particular account, based on
	 * client ID and account ID
	 * 
	 * @param clientId
	 * @param accountId
	 * @param amount
	 * @return
	 * @throws CustomParentException if client ID is Invalid
	 */
	@PutMapping("/clients/{clientId}/accounts")
	public Account depositMoneyIntoAccount(@PathVariable Long clientId, @RequestParam Long accountId,
			@RequestParam Double amount) throws CustomParentException {
		Optional<Client> client = clientRepository.findById(clientId);
		if (!client.isPresent()) {
			throw new ClientException("Invalid Client ID");
		}
		Optional<Account> account = accountRepository.findById(accountId);
		if (!account.isPresent()) {
			throw new AccountException("Invalid Account ID");
		}

		addAmount(accountId, amount);
		return accountRepository.save(account.get());
	}

	/**
	 * This method is responsible to credit money to an account
	 * 
	 * @param id
	 * @param amount
	 * @throws TransactionException, if account details are not available in
	 *                               database
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	public void addAmount(Long id, double amount) throws TransactionException {
		Optional<Account> optionalAccount = accountRepository.findById(id);
		if (!optionalAccount.isPresent()) {
			throw new TransactionException("Account not found " + id);
		}
		Account account = optionalAccount.get();
		double newBalance;
		if (account.getBalanceStatus().equals(BalanceStatus.CR)) {
			newBalance = account.getBalance() + amount;
		} else {
			if (account.getBalance() <= amount) {
				account.setBalanceStatus(BalanceStatus.CR);
			}
			newBalance = Math.abs(account.getBalance() - amount);
		}
		account.setBalance(newBalance);
	}

	/**
	 * 
	 * This method is responsible to debit money from account
	 * 
	 * @param id
	 * @param amount
	 * @throws TransactionException, If account details are not available in
	 *                               database.
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	public void removeAmount(Long id, double amount) throws TransactionException {
		Optional<Account> optionalAccount = accountRepository.findById(id);
		if (!optionalAccount.isPresent()) {
			throw new TransactionException("Account not found " + id);
		}
		Account account = optionalAccount.get();
		double newBalance;
		if (account.getBalanceStatus().equals(BalanceStatus.DR)) {
			newBalance = account.getBalance() + amount;
		} else {
			newBalance = account.getBalance() - amount;
			if (newBalance < 0) {
				account.setBalanceStatus(BalanceStatus.DR);
				newBalance = Math.abs(newBalance);
			}
		}
		account.setBalance(newBalance);
	}

}
