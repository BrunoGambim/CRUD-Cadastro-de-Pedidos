package br.com.brunogambim.CRUDCadastroDePedidos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Payment;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.PaymentRepository;

@Service
public class PaymentService {
	
	private PaymentRepository paymentRepository;
	
	@Autowired
	public PaymentService(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}
	
	public Payment insert(Payment payment) {
		payment.setId(null);
		return this.paymentRepository.save(payment);
	}
}
