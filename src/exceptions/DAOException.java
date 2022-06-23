package exceptions;

public class DAOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Exception ex;
	
	public DAOException(String message, Exception ex) {
		super(message);
		this.ex = ex;
	}
	
	/**
	 * 
	 * @return Exception
	 */
	public Exception getException() {
		return this.ex;
	}
	
}
