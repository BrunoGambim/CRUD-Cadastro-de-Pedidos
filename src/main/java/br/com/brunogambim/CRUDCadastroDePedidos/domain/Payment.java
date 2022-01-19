package br.com.brunogambim.CRUDCadastroDePedidos.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import br.com.brunogambim.CRUDCadastroDePedidos.enums.PaymentState;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payment implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer paymentState;
	
	public Payment() {
	}

	public Payment(Long id, PaymentState paymentState) {
		super();
		this.id = id;
		this.paymentState = paymentState == null ? null : paymentState.getCode();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PaymentState getPaymentState() {
		return PaymentState.toEnum(paymentState);
	}

	public void setPaymentState(PaymentState paymentState) {
		this.paymentState = paymentState.getCode();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		return Objects.equals(id, other.id);
	}
}
