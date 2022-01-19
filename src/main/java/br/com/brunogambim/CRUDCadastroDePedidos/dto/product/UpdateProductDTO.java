package br.com.brunogambim.CRUDCadastroDePedidos.dto.product;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Product;


public class UpdateProductDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
	private String name;
	
	private Double price;
	
	public UpdateProductDTO () {
	}

	public Product toEntity(Long id) {
		return new Product(id, this.name, this.price);
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
