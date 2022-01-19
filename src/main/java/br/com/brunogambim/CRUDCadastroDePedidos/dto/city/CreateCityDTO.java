package br.com.brunogambim.CRUDCadastroDePedidos.dto.city;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.City;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.city.state.CreateCityStateDTO;


public class CreateCityDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
	private String name;
	
	private CreateCityStateDTO state;
	
	public CreateCityDTO () {
	}
	
	public City toEntity() {
		return new City(null, this.name, this.state.toEntity());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CreateCityStateDTO getState() {
		return state;
	}

	public void setState(CreateCityStateDTO state) {
		this.state = state;
	}
}
