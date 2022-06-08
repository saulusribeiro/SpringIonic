package br.com.saulusribeiro.springbackend.services.exceptions;

public class AuthorizationException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	
	// Implementar Construtores
	
	public AuthorizationException(String msg) {
		
		super(msg);
	}
	// Sobrecarga de métodos construtores para identificar exceção e causa
	
	public AuthorizationException(String msg, Throwable cause) {
	
		super(msg, cause);
	}
}
