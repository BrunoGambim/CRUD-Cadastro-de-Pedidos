package br.com.brunogambim.CRUDCadastroDePedidos.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandartError{
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> fieldMessage = new ArrayList<>();

	public ValidationError() {
	}
	
	public ValidationError(Long timeStamp, Integer status, String error, String message, String path) {
		super(timeStamp, status, error, message, path);
	}
	
	public List<FieldMessage> getErrors() {
		return fieldMessage;
	}

	public void addField(FieldMessage fieldMessage) {
		this.fieldMessage.add(fieldMessage);
	}
	
	public void addFieldMessage(List<FieldMessage> fieldMessages) {
		this.fieldMessage.addAll(fieldMessages);
	}
}
