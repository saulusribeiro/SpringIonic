package br.com.saulusribeiro.springbackend.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.saulusribeiro.springbackend.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	@Value("${default.sender}")
	private String sender;
	@Override
	public void sendOrderConfirmation(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessage(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessage(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido Confirmado Código: "+obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	

}
