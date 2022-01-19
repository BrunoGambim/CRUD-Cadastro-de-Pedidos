package br.com.brunogambim.CRUDCadastroDePedidos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Client;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.ClientRepository;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService{
	
	@Autowired
	private ClientRepository clientRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Client client = clientRepository.findByEmail(email)
				.orElseThrow(() ->new UsernameNotFoundException(email));
		return new UserSS(client);
	}

}
