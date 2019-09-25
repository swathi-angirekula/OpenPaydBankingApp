package com.myproject.openpaydbankingapp.Exception;

/**
 * Constructs a new ClientException
 * @author Swathi Angirekula
 *
 */
public class ClientException extends CustomParentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3128681006635769411L;

	/**
	 * @param message
	 */
	public ClientException(String message) {
		super(message);
	}
}
