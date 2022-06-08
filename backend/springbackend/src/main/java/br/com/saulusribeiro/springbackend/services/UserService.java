package br.com.saulusribeiro.springbackend.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.saulusribeiro.springbackend.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		// retorna usuario logado no sistema
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}