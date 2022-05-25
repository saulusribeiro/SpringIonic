package br.com.saulusribeiro.springbackend.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.saulusribeiro.springbackend.domain.Categoria;


public class CategoriaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
    @Size(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
	private String nome;

	
	// Construtor vazio
	
	public CategoriaDTO() {
		
	}
	
	// Construtor a partir  de uma classe de dominio
	
	public CategoriaDTO(Categoria obj) {
		id = obj.getId();
		nome = obj.getNome();
	
	}
	
	// getters and setters
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	


}
