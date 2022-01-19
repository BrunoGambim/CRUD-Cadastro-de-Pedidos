package br.com.brunogambim.CRUDCadastroDePedidos.dto.order.payment;

import org.springframework.stereotype.Component;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.BankSlipPayment;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.CardPayment;
import br.com.brunogambim.CRUDCadastroDePedidos.domain.Payment;

@Component
public class PaymentAssembler {
	
	public GetPaymentDTO toModel(Payment payment) {
		GetPaymentDTO getPaymentDTO;
		
		if(payment instanceof BankSlipPayment) {
			BankSlipPayment bankSlipPayment = (BankSlipPayment) payment;
			getPaymentDTO = new GetBankSlipPaymentDTO(bankSlipPayment.getId(), bankSlipPayment.getPaymentState(),
					bankSlipPayment.getDueDate(), bankSlipPayment.getPaymentDate());
		} else {
			CardPayment cardPayment = (CardPayment) payment;
			getPaymentDTO = new GetCardPaymentDTO(cardPayment.getId(), cardPayment.getPaymentState(),
					cardPayment.getNumberOfInstallments());
		}
		
		return getPaymentDTO;
	}
}
