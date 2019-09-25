package com.myproject.openpaydbankingapp.Exception;

/**
 *
 * Constructs a new CustomParentException
 * @author Swathi Angirekula
 *
 */
public class CustomParentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3357355611549119734L;

	/**
	 * @param message
	 */
	CustomParentException(String message) {
		super(message);
	}
}
