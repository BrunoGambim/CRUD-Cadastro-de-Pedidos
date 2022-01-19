package br.com.brunogambim.CRUDCadastroDePedidos.dto.order.orderitem;

import java.io.Serializable;

import br.com.brunogambim.CRUDCadastroDePedidos.dto.product.GetProductDTO;

public class GetOrderItemDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer numberOfItens;
	private Double discount;
	private GetProductDTO product;
	private Double subtotal;

	public GetOrderItemDTO(Integer numberOfItens, Double discount, GetProductDTO product, Double subtotal) {
		super();
		this.numberOfItens = numberOfItens;
		this.discount = discount;
		this.product = product;
		this.subtotal = subtotal;
	}

	public Integer getNumberOfItens() {
		return numberOfItens;
	}

	public GetProductDTO getProduct() {
		return product;
	}

	public Double getDiscount() {
		return discount;
	}

	public Double getSubtotal() {
		return subtotal;
	}
}
