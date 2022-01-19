package br.com.brunogambim.CRUDCadastroDePedidos.dto.address;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.City;

public class CreateAddressCityDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private Long id;
	
	public CreateAddressCityDTO () {
	}

	public City toEntity() {
		return new City(id, null, null);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
