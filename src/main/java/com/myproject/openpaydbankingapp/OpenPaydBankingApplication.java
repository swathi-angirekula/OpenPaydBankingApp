package com.myproject.openpaydbankingapp;

import java.util.Scanner;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 *
 * This program implements a Banking application that creates new client and
 * their corresponding accounts and transfers money between the accounts.
 * 
 * @author Swathi Angirekula
 *
 */
@SpringBootApplication
@EnableJpaAuditing
public class OpenPaydBankingApplication {
	private static final Logger LOGGER = Logger.getLogger(OpenPaydBankingApplication.class.getName());

	/**
	 * Main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(OpenPaydBankingApplication.class, args);
		OpenPaydBankingApplication app = new OpenPaydBankingApplication();
		app.displayMessage();
		Scanner sc = new Scanner(System.in);
		String confirmExit = "";

		while (!confirmExit.equalsIgnoreCase("Y")) {
			confirmExit = sc.nextLine();
		}
		sc.close();
		System.exit(0);
	}

	/**
	 * This method is used to display static message at the application Startup for
	 * the users.
	 */
	public void displayMessage() {
		String msg = "\n********************************************\n"
				+ "The OpenPayd Banking application is started.\n" + "********************************************\n"
				+ "Refer to the Swagger documentation at : http://localhost:8080/swagger-ui.html\n"
				+ "-----------------------------------------------------------------------------\n"
				+ "Enter Y/y to stop.\n";
		LOGGER.info(msg);
	}
}
