package br.com.brunogambim.CRUDCadastroDePedidos.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Client;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Order;

public interface EmailService {
	void sendOrderConfirmationEmail(Order order);
	void sendEmail (SimpleMailMessage msg);
	void sendNewPasswordEmail(Client client, String newPassword);
}
