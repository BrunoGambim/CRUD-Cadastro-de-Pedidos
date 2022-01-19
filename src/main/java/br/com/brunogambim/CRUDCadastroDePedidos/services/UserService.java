package br.com.brunogambim.CRUDCadastroDePedidos.services;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.brunogambim.CRUDCadastroDePedidos.enums.Perfil;
import br.com.brunogambim.CRUDCadastroDePedidos.security.UserSS;

public class UserService {
	
	public static Optional<UserSS> authenticated() {
		try {
			return Optional.of((UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	public static Boolean isAuthorized(Long id) {
		Optional<UserSS>  user = UserService.authenticated();
		return isAuthorized() || (!user.isEmpty() && user.get().getId().equals(id));
	}
	
	public static Boolean isAuthorized(String email) {
		Optional<UserSS>  user = UserService.authenticated();
		return isAuthorized() || (!user.isEmpty() && user.get().getUsername().equals(email));
	}
	
	public static Boolean isAuthorized() {
		Optional<UserSS>  user = UserService.authenticated();
		if(!user.isEmpty() && (user.get().hasRole(Perfil.ADMIN))){
			return true;
		}
		return false;
	}
}
