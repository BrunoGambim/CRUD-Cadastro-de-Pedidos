package br.com.brunogambim.CRUDCadastroDePedidos.services;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService{
	
	private static final Logger logger = LoggerFactory.getLogger(MockEmailService.class);
	
	@Autowired
	private HttpServletResponse response;

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		logger.info("Controlando envio de email");
		logger.info(msg.toString());
		logger.info("Email enviado");
		String message = msg.getText();
		if(message.contains("Nova senha:")) {
			response.addHeader("mail_message", message);
			response.addHeader("access-control-expose-headers","mail_message");
		}
	}

}
