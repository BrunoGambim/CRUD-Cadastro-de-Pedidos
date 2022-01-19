package br.com.brunogambim.CRUDCadastroDePedidos.dto.order;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Order;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.order.orderitem.CreateOrderItemDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.order.payment.CreateOrderPaymentDTO;

public class CreateOrderDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private CreateOrderClientDTO client;
	private CreateOrderAddressDTO deliveryAddress;
	private CreateOrderPaymentDTO payment;
	private Set<CreateOrderItemDTO> items = new HashSet<>();
	
	public CreateOrderDTO() {
	}

	public Order toEntity() {
		Order order = new Order(null, null, this.client.toEntity(), this.deliveryAddress.toEntity());
		items.stream().forEach(item -> order.addItem(item.toEntity()));
		order.setPayment(payment.toEntity());
		return order;
	}
	
	
	
	public void setClient(CreateOrderClientDTO client) {
		this.client = client;
	}

	public void setDeliveryAddress(CreateOrderAddressDTO address) {
		this.deliveryAddress = address;
	}

	public void setPayment(CreateOrderPaymentDTO payment) {
		this.payment = payment;
	}

	public void setItems(Set<CreateOrderItemDTO> items) {
		this.items = items;
	}
}
