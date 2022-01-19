package br.com.brunogambim.CRUDCadastroDePedidos.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Client;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Order;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Order order) {
		SimpleMailMessage mailMessage = prepareSimpleMailMessageFromOrder(order);
		sendEmail(mailMessage);
	}
	
	protected SimpleMailMessage prepareSimpleMailMessageFromOrder(Order order) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(order.getClient().getEmail());
		mailMessage.setFrom(sender);
		mailMessage.setSubject("Pedido confirmado: Código "+ order.getId());
		mailMessage.setSentDate(new Date(System.currentTimeMillis()));
		mailMessage.setText(order.toString());
		return mailMessage;
	}
	
	@Override
	public void sendNewPasswordEmail(Client client, String newPassword) {
		SimpleMailMessage mailMessage = prepareSimplePasswordEmailessage(client, newPassword);
		sendEmail(mailMessage);
	}
	
	protected SimpleMailMessage prepareSimplePasswordEmailessage(Client client, String newPassword) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(client.getEmail());
		mailMessage.setFrom(sender);
		mailMessage.setSubject("Solicitação de nova senha");
		mailMessage.setSentDate(new Date(System.currentTimeMillis()));
		mailMessage.setText("Nova senha: " + newPassword);
		return mailMessage;
	}
	
}
