package br.com.brunogambim.CRUDCadastroDePedidos.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.brunogambim.CRUDCadastroDePedidos.dto.auth.CredentialsDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.security.utils.JWTUtil;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
	private JWTUtil jwtUtil;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			CredentialsDTO credentialsDTO = new ObjectMapper().readValue(request.getInputStream(), CredentialsDTO.class);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					credentialsDTO.getEmail(), credentialsDTO.getPassword(), new ArrayList<>());
			Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			return auth;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((UserSS)authResult.getPrincipal()).getUsername();
		String jwtToken = jwtUtil.generateToken(username);
		response.addHeader("Authorization","Bearer " + jwtToken);
		response.addHeader("access-control-expose-headers","Authorization");
	}
	
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler{
		
		private String json(){
			long date = new Date().getTime();
			return "{\"timestamp\" : " + date + ","+
					"\"status:401\" : " +
					"\"error\" : \"Não Autorizado\" ," +
					"\"message\" : \"Email ou senha inválidos\" ," +
					"\"path\" : \"/login\"}";
		}

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json");
			response.getWriter().append(json());
		}
		
	}
}
