package br.com.brunogambim.CRUDCadastroDePedidos.dto.order.orderitem;

import java.io.Serializable;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Product;

public class CreateOrderProductDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	public CreateOrderProductDTO() {
	}

	public Product toEntity() {
		return new Product(id, null, null);
	}

	public void setId(Long id) {
		this.id = id;
	}
}
