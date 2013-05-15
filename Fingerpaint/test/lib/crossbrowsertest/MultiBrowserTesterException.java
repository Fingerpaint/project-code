package lib.crossbrowsertest;

/**
 * Generic Exception class thrown if there is an error with the {@link MultiBrowserTester}.
 * 
 * @author Lasse Blaauwbroek
 */
public class MultiBrowserTesterException extends Exception {

	/**
	 * Generated serial version uid
	 */
	private static final long serialVersionUID = 885991581283809151L;
	
	/**
	 * Standard constructor with error string.
	 * 
	 * @param error A message describing the exception
	 */
	public MultiBrowserTesterException(String error) {
		super(error);
	}
	
	/**
	 * Standard constructor with error string and cause.
	 * 
	 * @param error A message describing the exception
	 * @param cause The exception causing this exception
	 */
	public MultiBrowserTesterException(String error, Throwable cause) {
		super(error, cause);
	}

}