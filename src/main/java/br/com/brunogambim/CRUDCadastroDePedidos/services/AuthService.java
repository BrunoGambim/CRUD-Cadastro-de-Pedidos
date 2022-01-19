package br.com.brunogambim.CRUDCadastroDePedidos.services;

import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Client;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.AuthorizationException;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.ObjectNotFoundException;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.ClientRepository;
import br.com.brunogambim.CRUDCadastroDePedidos.security.UserSS;
import br.com.brunogambim.CRUDCadastroDePedidos.security.utils.JWTUtil;

@Service
public class AuthService {
	
	private ClientRepository clientRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private EmailService emailService;
	private JWTUtil jwtUtil;
	
	private Random random = new Random();
	
	@Autowired
	public AuthService(ClientRepository clientRepository,BCryptPasswordEncoder bCryptPasswordEncoder,EmailService emailService,
			JWTUtil jwtUtil) {
		this.clientRepository = clientRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.emailService = emailService;
		this.jwtUtil = jwtUtil;
	}
	
	public void refreshToken(HttpServletResponse httpResponse) {
		UserSS user = (UserSS) UserService.authenticated().orElseThrow(() -> new AuthorizationException());
		String jwtToken = this.jwtUtil.generateToken(user.getUsername());
		httpResponse.addHeader("Authorization","Bearer " + jwtToken);
		httpResponse.addHeader("access-control-expose-headers","Authorization");
	}
	
	public void sendNewPassword(String email) {
		Client client = clientRepository.findByEmail(email).orElseThrow(()->
			new ObjectNotFoundException(email, Client.class)
		);
		String newPassword = newPassword();
		client.setPassword(bCryptPasswordEncoder.encode(newPassword));
		clientRepository.save(client);
		emailService.sendNewPasswordEmail(client, newPassword);
	}

	private String newPassword() {
		char randomVet[] = new char[10];
		for(int i = 0; i < randomVet.length;i++) {
			randomVet[i] = randomChar();
		}
		return new String(randomVet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		if(opt == 0) {
			return (char) (random.nextInt(10)+48);
		}else if(opt == 1){
			return (char) (random.nextInt(26)+65);
		}else {
			return (char) (random.nextInt(26)+97);
		}
	}
}
