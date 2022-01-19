package br.com.brunogambim.CRUDCadastroDePedidos.dto.order.payment;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.brunogambim.CRUDCadastroDePedidos.enums.PaymentState;

@JsonTypeName("bankSlipPayment")
public class GetBankSlipPaymentDTO extends GetPaymentDTO{
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date dueDate;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date paymentDate;

	public GetBankSlipPaymentDTO(Long id, PaymentState paymentState, Date dueDate, Date paymentDate) {
		super(id, paymentState);
		this.dueDate = dueDate;
		this.paymentDate = paymentDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}
}
