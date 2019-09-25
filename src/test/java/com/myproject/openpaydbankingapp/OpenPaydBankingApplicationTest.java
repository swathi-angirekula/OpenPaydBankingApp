package com.myproject.openpaydbankingapp;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import org.apache.log4j.Appender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author Swathi Angirekula
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class OpenPaydBankingApplicationTest {

	OpenPaydBankingApplication bankingApp = mock(OpenPaydBankingApplication.class);
	@Mock
	private Appender mockAppender;

	/**
	 * This test demonstrates the execution of display message.
	 * 
	 */
	@Test
	public void testDisplayMessage() {
		try {
			bankingApp.displayMessage();
		} catch (Exception e) {
			fail("Should not have thrown any exception");
		}
	}

}
