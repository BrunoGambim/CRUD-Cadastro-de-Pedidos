package br.com.brunogambim.CRUDCadastroDePedidos.dto.city;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cities", itemRelation = "city")
public class GetCityDTO extends RepresentationModel<GetCityDTO> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;

	public GetCityDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
