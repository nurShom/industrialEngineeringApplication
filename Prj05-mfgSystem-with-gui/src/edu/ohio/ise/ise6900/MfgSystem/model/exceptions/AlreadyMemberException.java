/**
 * 
 */
package edu.ohio.ise.ise6900.MfgSystem.model.exceptions;

/**
 * @author na551411
 *
 */
public class AlreadyMemberException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4760987247991557456L;

	/**
	 * 
	 */
	public AlreadyMemberException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public AlreadyMemberException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public AlreadyMemberException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AlreadyMemberException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public AlreadyMemberException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
