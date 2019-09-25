package com.myproject.openpaydbankingapp.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.openpaydbankingapp.model.Client;
import com.myproject.openpaydbankingapp.repository.ClientRepository;

/**
 * 
 * This application is responsible to create new client and retrieves client
 * details.
 * 
 * @author Swathi Angirekula
 *
 */
@RestController
public class ClientController {
	@Autowired
	private ClientRepository clientRepository;

	/**
	 * This method retrieves all client details, available in application.
	 * 
	 * @return list of client details
	 */
	@GetMapping("/clients")
	public List<Client> getAllClients() {
		return clientRepository.findAll();
	}

	/**
	 * This method creates new client in database.
	 * 
	 * @param client
	 * @return client details.
	 */
	@PostMapping("/clients")
	public Client createClient(@Valid @RequestBody Client client) {
		return clientRepository.save(client);
	}

	/**
	 * This method retrieves client details based on client ID
	 * 
	 * @param clientId
	 * @return client details.
	 */
	@GetMapping("/clients/{clientId}")
	public Optional<Client> getClientById(@PathVariable Long clientId) {
		return clientRepository.findById(clientId);
	}

}
