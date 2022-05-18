package br.com.saulusribeiro.springbackend.services.exceptions;

public class DataIntegrityException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	
	// Implementar Construtores
	
	public DataIntegrityException(String msg) {
		
		super(msg);
	}
	// Sobrecarga de métodos construtores para identificar exceção e causa
	
	public DataIntegrityException(String msg, Throwable cause) {
	
		super(msg, cause);
	}
}
