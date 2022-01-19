package br.com.brunogambim.CRUDCadastroDePedidos.domain;

import java.util.Date;

import javax.persistence.Entity;

import br.com.brunogambim.CRUDCadastroDePedidos.enums.PaymentState;

@Entity
public class BankSlipPayment extends Payment{
	private static final long serialVersionUID = 1L;
	
	private Date dueDate;
	
	private Date paymentDate;
	
	public BankSlipPayment() {
	}

	public BankSlipPayment(Long id, PaymentState paymentState, Date dueDate, Date paymentDate) {
		super(id, paymentState);
		this.dueDate = dueDate;
		this.paymentDate = paymentDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
}
