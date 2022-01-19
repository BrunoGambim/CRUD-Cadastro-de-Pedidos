package br.com.brunogambim.CRUDCadastroDePedidos.dto.order;

import java.io.Serializable;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Client;

public class CreateOrderClientDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	public CreateOrderClientDTO() {
	}

	public Client toEntity() {
		return new Client(id, null, null, null, null, null);
	}

	public void setId(Long id) {
		this.id = id;
	}
}
