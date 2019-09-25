package com.myproject.openpaydbankingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.openpaydbankingapp.model.Transaction;

/**
 * @author Swathi Angirekula
 *
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
