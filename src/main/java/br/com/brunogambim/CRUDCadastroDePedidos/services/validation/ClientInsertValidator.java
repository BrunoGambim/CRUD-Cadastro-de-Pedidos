package br.com.brunogambim.CRUDCadastroDePedidos.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.brunogambim.CRUDCadastroDePedidos.dto.client.CreateClientDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.enums.ClientType;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.ClientRepository;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.exception.FieldMessage;
import br.com.brunogambim.CRUDCadastroDePedidos.services.validation.utils.CPFOrCNPJValidator;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, CreateClientDTO>{
	
	private CPFOrCNPJValidator cPFOrCNPJValidator;
	private ClientRepository clientRepository;
	
	@Autowired
	public ClientInsertValidator(CPFOrCNPJValidator cPFOrCNPJValidator, ClientRepository clientRepository) {
		this.cPFOrCNPJValidator = cPFOrCNPJValidator;
		this.clientRepository = clientRepository;
	}

	@Override
	public void initialize(ClientInsert constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(CreateClientDTO value, ConstraintValidatorContext context) {
		List<FieldMessage> fieldMesssageList = new ArrayList<>();
		
		if(value.getClientType().equals((Integer)ClientType.NATURALPERSON.getCode())
				&& !cPFOrCNPJValidator.isValidCPF(value.getCpfOrCnpj())) {
			fieldMesssageList.add(new FieldMessage("cpfOrCnpj", "CPF inválido"));
		}
		if(value.getClientType().equals((Integer)ClientType.LEGALPERSON.getCode())
				&& !cPFOrCNPJValidator.isValidCNPJ(value.getCpfOrCnpj())) {
			fieldMesssageList.add(new FieldMessage("cpfOrCnpj", "CNPJ inválido"));
		}
		
		if(!this.clientRepository.findByEmail(value.getEmail()).isEmpty()) {
			fieldMesssageList.add(new FieldMessage("email", "Email já está em uso"));
		}
		
		fieldMesssageList.forEach((fieldMessage)->{
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
			.addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
		});
		
		return fieldMesssageList.isEmpty();
	}

}
