package com.myproject.openpaydbankingapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.myproject.openpaydbankingapp.model.Address;
import com.myproject.openpaydbankingapp.model.Client;
import com.myproject.openpaydbankingapp.repository.ClientRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application.properties")
public class ClientControllerTest {

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	private MockMvc mvc;

	/**
	 * Given the input json is rightly created, when the DB contains the clients ,
	 * the URL should return the clients
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetAllClients() throws Exception {
		// Given
		Address address = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Client client = Client.builder().name("Eliyaz").surname("Shaik").primaryAddress(address)
				.secondaryAddress(address).build();
		// When
		clientRepository.save(client);
		MvcResult result = mvc.perform(get("/clients/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		// Then
		Assert.assertTrue(content.contains(address.getAddressLine1()));
		Assert.assertTrue(content.contains(client.getName()));
	}

	/**
	 * Given the input json is rightly created, when the client is Created using
	 * endpoint POST , the resource should be created
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateClient() throws Exception {
		// Given
		Address address = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Client client = Client.builder().name("Eliyaz").surname("Shaik").primaryAddress(address)
				.secondaryAddress(address).build();
		// When
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(client);
		MvcResult result = mvc.perform(post("/clients/").content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String responseContent = result.getResponse().getContentAsString();
		// Then
		Assert.assertTrue(responseContent.contains(address.getAddressLine1()));
		Assert.assertTrue(responseContent.contains(client.getName()));
	}

	/**
	 * Given the client is available in DB, when a client is retrieved , the
	 * endpoint should return the right client.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetClientById() throws Exception {
		// Given
		Address address = Address.builder().addressLine1("Line1").addressLine2("Line2").city("London").country("UK")
				.build();
		Client client = Client.builder().name("Eliyaz").surname("Shaik").primaryAddress(address)
				.secondaryAddress(address).build();
		// When
		clientRepository.save(client);
		Long clientId = clientRepository.findAll().get(0).getId();
		MvcResult result = mvc.perform(get("/clients/" + clientId.toString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String responseContent = result.getResponse().getContentAsString();
		// Then
		Assert.assertTrue(responseContent.contains(address.getAddressLine1()));
		Assert.assertTrue(responseContent.contains(client.getName()));
	}

}
