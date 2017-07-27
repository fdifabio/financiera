package ar.edu.unrn.lia.exception;

/**
 * Clase base para las excepciones de negocio
 * 
 */
public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String keyMessage;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(final String message, Throwable t, String keyMessage) {
		super(message, t);
		this.keyMessage = keyMessage;
	}

	public String getKeyMessage() {
		return keyMessage;
	}

	public void setKeyMessage(String keyMessage) {
		this.keyMessage = keyMessage;
	}

}
