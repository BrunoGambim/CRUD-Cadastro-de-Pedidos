package br.com.brunogambim.CRUDCadastroDePedidos.dto.order.payment;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.com.brunogambim.CRUDCadastroDePedidos.enums.PaymentState;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,property = "@type")
public abstract class GetPaymentDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer paymentState;

	public GetPaymentDTO(Long id, PaymentState paymentState) {
		super();
		this.paymentState = paymentState == null ? null : paymentState.getCode();
	}

	public PaymentState getPaymentState() {
		return PaymentState.toEnum(paymentState);
	}
}
