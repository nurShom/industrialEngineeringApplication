/**
 * 
 */
package edu.ohio.ise.ise6900.MfgSystem.model.exceptions;

/**
 * @author na551411
 *
 */
public class OverlappingStateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4879903211197389718L;

	/**
	 * 
	 */
	public OverlappingStateException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public OverlappingStateException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public OverlappingStateException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public OverlappingStateException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public OverlappingStateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
