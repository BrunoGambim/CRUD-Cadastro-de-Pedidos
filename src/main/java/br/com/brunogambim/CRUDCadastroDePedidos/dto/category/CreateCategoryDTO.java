package br.com.brunogambim.CRUDCadastroDePedidos.dto.category;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Category;


public class CreateCategoryDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
	private String name;
	
	public CreateCategoryDTO () {
	}
	
	public Category toEntity() {
		return new Category(null, this.name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
