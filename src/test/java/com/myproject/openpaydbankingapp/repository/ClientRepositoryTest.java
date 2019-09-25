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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.myproject.openpaydbankingapp.model.Address;
import com.myproject.openpaydbankingapp.model.Client;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ClientRepositoryTest {

	@Autowired
	private ClientRepository clientRepository;

	// This test is used to verify if the account is saved in the DB.
	@Test
	public void testClientSave() {

		// Given
		Address address = Address.builder().id(new Random().nextLong()).addressLine1("Line1").addressLine2("Line2")
				.city("London").country("UK").build();
		Client clientSaved = Client.builder().id(new Random().nextLong()).name("Swathi").surname("Angirekula")
				.primaryAddress(address).secondaryAddress(address).build();
		// When
		clientRepository.save(clientSaved);

		List<Client> retrievedClientList = clientRepository.findAll();
		// Then
		Assert.assertThat(retrievedClientList.isEmpty(), is(false));
		Assert.assertEquals(clientSaved.toString(), retrievedClientList.get(0).toString());
	}

	@Test
	public void testClientSave_multipleClients() {

		// Given
		Address address = Address.builder().id(new Random().nextLong()).addressLine1("Line1").addressLine2("Line2")
				.city("London").country("UK").build();
		for (int i = 0; i < 10; i++) {
			Client clientSaved = Client.builder().id(new Random().nextLong()).name("Swathi").surname("Angirekula")
					.primaryAddress(address).secondaryAddress(address).build();
			// When
			clientRepository.save(clientSaved);
		}
		List<Client> retrievedClientList = clientRepository.findAll();
		// Then
		Assert.assertEquals(retrievedClientList.size(), 10);
	}

}
