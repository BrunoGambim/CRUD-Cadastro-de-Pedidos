package br.com.brunogambim.CRUDCadastroDePedidos.dto.product;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import br.com.brunogambim.CRUDCadastroDePedidos.dto.category.GetCategoryDTO;

@Relation(collectionRelation = "products", itemRelation = "product")
public class GetProductDTO extends RepresentationModel<GetCategoryDTO> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Double price;

	public GetProductDTO(Long id, String name, Double price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
