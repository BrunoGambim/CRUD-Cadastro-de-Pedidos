package br.com.brunogambim.CRUDCadastroDePedidos.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.BankSlipPayment;

@Service
public class BankSlipService {

	public void fillBankSlipPayment(BankSlipPayment bankSlipPayment, Date createdAt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(createdAt);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		bankSlipPayment.setDueDate(calendar.getTime());
	}

}
