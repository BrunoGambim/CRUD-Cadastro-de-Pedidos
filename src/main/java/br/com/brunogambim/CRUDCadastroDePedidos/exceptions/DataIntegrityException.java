package br.com.brunogambim.CRUDCadastroDePedidos.exceptions;

public class DataIntegrityException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DataIntegrityException(String operationName, Class<?> classe) {
		super(message(operationName, classe));
	}
	
	public DataIntegrityException(String operationName, Class<?> classe, Throwable cause) {
		super(message(operationName, classe), cause);
	}
	
	private static String message(String operationName, Class<?> classe) {
		return "Não é possivel concluir a operação de "+ operationName +" no objeto de tipo: "+ classe.getSimpleName() + " por que viola a integridade dos dados.";
	}
}
