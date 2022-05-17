package br.com.saulusribeiro.springbackend.repositories;

import org.springframework.beans.factory.annotation.Autowired;

public class Snippet {
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
}

