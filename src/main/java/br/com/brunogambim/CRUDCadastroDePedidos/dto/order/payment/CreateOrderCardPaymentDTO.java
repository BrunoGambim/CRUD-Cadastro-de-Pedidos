package br.com.brunogambim.CRUDCadastroDePedidos.dto.order.payment;

import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.CardPayment;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Payment;

@JsonTypeName("cardPayment")
public class CreateOrderCardPaymentDTO extends CreateOrderPaymentDTO{
	private static final long serialVersionUID = 1L;
	
	private Integer numberOfInstallments;
	
	public CreateOrderCardPaymentDTO() {
	}

	public void setNumberOfInstallments(Integer numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}

	@Override
	public Payment toEntity() {
		return new CardPayment(null, null, numberOfInstallments);
	}
}
