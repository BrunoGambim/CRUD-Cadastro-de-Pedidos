package br.com.brunogambim.CRUDCadastroDePedidos.dto.order.payment;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.BankSlipPayment;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Payment;

@JsonTypeName("bankSlipPayment")
public class CreateOrderBankSlipPaymentDTO extends CreateOrderPaymentDTO{
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date dueDate;

	public CreateOrderBankSlipPaymentDTO(){
	}
	
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public Payment toEntity() {
		return new BankSlipPayment(null, null, dueDate, null);
	}
}
