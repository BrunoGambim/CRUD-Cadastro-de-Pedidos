package br.com.brunogambim.CRUDCadastroDePedidos.services.validation.utils;

public interface CPFOrCNPJValidator {
	
	 public boolean isValidCPF(String cpf);
	 
	 public boolean isValidCNPJ(String cpf);
}
