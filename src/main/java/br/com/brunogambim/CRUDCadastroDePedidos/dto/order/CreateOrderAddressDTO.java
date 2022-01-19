package br.com.brunogambim.CRUDCadastroDePedidos.dto.order;

import java.io.Serializable;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Address;

public class CreateOrderAddressDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	public CreateOrderAddressDTO() {
	}

	public Address toEntity() {
		return new Address(id, null, null, null, null, null, null);
	}

	public void setId(Long id) {
		this.id = id;
	}
}
