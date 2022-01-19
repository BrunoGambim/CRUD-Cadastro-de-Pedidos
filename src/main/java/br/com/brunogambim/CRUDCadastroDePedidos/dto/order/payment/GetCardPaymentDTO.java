package br.com.brunogambim.CRUDCadastroDePedidos.dto.order.payment;

import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.brunogambim.CRUDCadastroDePedidos.enums.PaymentState;

@JsonTypeName("cardPayment")
public class GetCardPaymentDTO extends GetPaymentDTO{
	private static final long serialVersionUID = 1L;
	
	private Integer numberOfInstallments;

	public GetCardPaymentDTO(Long id, PaymentState paymentState, Integer numberOfInstallments) {
		super(id, paymentState);
		this.numberOfInstallments= numberOfInstallments;
	}

	public Integer getNumberOfInstallments() {
		return numberOfInstallments;
	}
}
