package com.myproject.openpaydbankingapp.Exception;

/**
 * Constructs a new AccountException
 * 
 * @author Swathi Angirekula
 *
 */
public class AccountException extends CustomParentException {

	private static final long serialVersionUID = -3128681006635769411L;

	public AccountException(String message) {
		super(message);
	}
}
