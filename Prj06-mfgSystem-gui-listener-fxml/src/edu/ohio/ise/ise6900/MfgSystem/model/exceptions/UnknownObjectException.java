/**
 * 
 */
package edu.ohio.ise.ise6900.MfgSystem.model.exceptions;

/**
 * @author na551411
 *
 */
public class UnknownObjectException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3406713127174917318L;

	/**
	 * 
	 */
	public UnknownObjectException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public UnknownObjectException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public UnknownObjectException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UnknownObjectException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public UnknownObjectException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
