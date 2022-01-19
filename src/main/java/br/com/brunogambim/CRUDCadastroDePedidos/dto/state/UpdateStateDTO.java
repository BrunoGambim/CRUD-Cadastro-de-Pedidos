package br.com.brunogambim.CRUDCadastroDePedidos.dto.state;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.State;


public class UpdateStateDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
	private String name;
	
	public UpdateStateDTO () {
	}
	
	public State toEntity(Long id) {
		return new State(id, this.name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
