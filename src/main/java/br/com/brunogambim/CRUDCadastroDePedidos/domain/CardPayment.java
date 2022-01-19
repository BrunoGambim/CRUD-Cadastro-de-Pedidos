package br.com.brunogambim.CRUDCadastroDePedidos.domain;

import javax.persistence.Entity;

import br.com.brunogambim.CRUDCadastroDePedidos.enums.PaymentState;

@Entity
public class CardPayment extends Payment{
	private static final long serialVersionUID = 1L;
	
	private Integer numberOfInstallments;
	
	public CardPayment() {
	}

	public CardPayment(Long id, PaymentState paymentState, Integer numberOfInstallments) {
		super(id, paymentState);
		this.numberOfInstallments= numberOfInstallments;
	}

	public Integer getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public void setNumberOfInstallments(Integer numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}
}
