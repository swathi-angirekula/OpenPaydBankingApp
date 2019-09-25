package com.myproject.openpaydbankingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myproject.openpaydbankingapp.model.Account;
import com.myproject.openpaydbankingapp.model.Client;

/**
 * @author Swathi Angirekula
 *
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query("select a from Account a where a.client = :client")
	List<Account> findAllAccountsByClient(Client client);
}
