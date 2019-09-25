package com.myproject.openpaydbankingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.openpaydbankingapp.model.Client;

/**
 * @author Swathi Angirekula
 *
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
