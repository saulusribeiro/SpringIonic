package br.com.saulusribeiro.springbackend.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.saulusribeiro.springbackend.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmation(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);

}
