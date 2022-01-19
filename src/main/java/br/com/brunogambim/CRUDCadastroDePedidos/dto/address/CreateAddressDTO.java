package br.com.brunogambim.CRUDCadastroDePedidos.dto.address;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Address;

public class CreateAddressDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String street;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String number;
	private String complement;
	private String district;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String cep;
	
	private CreateAddressCityDTO city;

	public CreateAddressDTO() {
	}

	public Address toEntity() {
		return new Address(null, street, number, complement, district, cep, city.toEntity());
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public CreateAddressCityDTO getCity() {
		return city;
	}

	public void setCity(CreateAddressCityDTO city) {
		this.city = city;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
}
