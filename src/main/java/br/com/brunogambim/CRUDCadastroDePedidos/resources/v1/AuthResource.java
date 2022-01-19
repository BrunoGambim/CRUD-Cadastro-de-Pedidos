package br.com.brunogambim.CRUDCadastroDePedidos.resources.v1;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.brunogambim.CRUDCadastroDePedidos.dto.auth.EmailDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.services.AuthService;

@RestController
@RequestMapping(value = "/v1/auth")
public class AuthResource {
	
	private AuthService authService;
	
	@Autowired
	public AuthResource(AuthService authService) {
		this.authService = authService;
	}
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse httpResponse){
		this.authService.refreshToken(httpResponse);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgotPassword(@Valid @RequestBody EmailDTO emailDTO){
		authService.sendNewPassword(emailDTO.getEmail());
		return ResponseEntity.noContent().build();
	}

}
