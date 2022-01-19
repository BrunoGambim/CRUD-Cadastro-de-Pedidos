package br.com.brunogambim.CRUDCadastroDePedidos.dto.order.orderitem;

import java.io.Serializable;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.OrderItem;

public class CreateOrderItemDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer numberOfItens;
	private CreateOrderProductDTO product;
	
	public CreateOrderItemDTO() {
	}
	
	public OrderItem toEntity() {
		return new OrderItem(product.toEntity(), 0D, numberOfItens);
	}

	public void setNumberOfItens(Integer numberOfItens) {
		this.numberOfItens = numberOfItens;
	}

	public void setProduct(CreateOrderProductDTO product) {
		this.product = product;
	}
	
}
