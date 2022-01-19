package br.com.brunogambim.CRUDCadastroDePedidos.dto.city.state;

import java.io.Serializable;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.State;

public class CreateCityStateDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	public CreateCityStateDTO() {
	}

	public State toEntity() {
		return new State(id, null);
	}

	public void setId(Long id) {
		this.id = id;
	}
}
