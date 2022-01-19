package br.com.brunogambim.CRUDCadastroDePedidos.dto.order;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.brunogambim.CRUDCadastroDePedidos.dto.order.orderitem.GetOrderItemDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.order.payment.GetPaymentDTO;

@Relation(collectionRelation = "orders", itemRelation = "order")
public class GetOrderDTO extends RepresentationModel<GetOrderDTO> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date createdAt;
	private GetPaymentDTO payment;
	private Set<GetOrderItemDTO> items = new HashSet<>();
	private Double total;
	
	public GetOrderDTO(Long id, Date createdAt,
			GetPaymentDTO payment, Set<GetOrderItemDTO> items, Double total) {
		super();
		this.id = id;
		this.createdAt = createdAt;
		this.payment = payment;
		this.items = items;
		this.total = total;
	}
	
	public Long getId() {
		return id;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public GetPaymentDTO getPayment() {
		return payment;
	}
	
	public Set<GetOrderItemDTO> getItems() {
		return items;
	}
	
	public Double getTotal() {
		return total;
	}
}
