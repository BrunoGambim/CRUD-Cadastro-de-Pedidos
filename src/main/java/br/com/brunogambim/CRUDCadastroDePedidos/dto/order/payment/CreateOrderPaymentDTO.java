package br.com.brunogambim.CRUDCadastroDePedidos.dto.order.payment;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Payment;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,property = "@type")
public abstract class CreateOrderPaymentDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public CreateOrderPaymentDTO() {
	}
	
	public abstract Payment toEntity();
}
