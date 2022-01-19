package br.com.brunogambim.CRUDCadastroDePedidos.dto.address;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import br.com.brunogambim.CRUDCadastroDePedidos.dto.city.GetCityDTO;

@Relation(collectionRelation = "addresses", itemRelation = "address")
public class GetAddressDTO extends RepresentationModel<GetAddressDTO>  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String street;
	private String number;
	private String complement;
	private String district;
	private String cep;	
	private GetCityDTO city;

	public GetAddressDTO(Long id, String street, String number, String complement, String district, String cep,
			GetCityDTO city) {
		this.id = id;
		this.street = street;
		this.number = number;
		this.complement = complement;
		this.district = district;
		this.cep = cep;
		this.city = city;
	}

	public Long getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	public String getComplement() {
		return complement;
	}

	public String getDistrict() {
		return district;
	}

	public String getCep() {
		return cep;
	}
	
	public String getStreet() {
		return street;
	}

	public GetCityDTO getCity() {
		return city;
	}
}
