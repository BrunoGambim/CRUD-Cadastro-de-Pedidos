package br.com.brunogambim.CRUDCadastroDePedidos.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Client;
import br.com.brunogambim.CRUDCadastroDePedidos.enums.Perfil;

public class UserSS implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserSS(Client client) {
		super();
		this.id = client.getId();
		this.email =client.getEmail();
		this.password = client.getPassword();
		this.authorities = client.getPerfis().stream().map(perfil -> new SimpleGrantedAuthority(perfil.getDescription())).collect(Collectors.toSet());
	}
	
	public UserSS(Long id, String email, String password, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = perfis.stream().map(perfil -> new SimpleGrantedAuthority(perfil.getDescription())).collect(Collectors.toSet());
	}

	public Long getId() {
		return this.id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean hasRole(Perfil perfil) {
		return this.authorities.contains(new SimpleGrantedAuthority(perfil.getDescription()));
	}

}
