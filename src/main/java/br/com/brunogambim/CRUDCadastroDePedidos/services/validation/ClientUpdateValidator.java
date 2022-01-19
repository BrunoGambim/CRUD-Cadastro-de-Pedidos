package br.com.brunogambim.CRUDCadastroDePedidos.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Client;
import br.com.brunogambim.CRUDCadastroDePedidos.dto.client.UpdateClientDTO;
import br.com.brunogambim.CRUDCadastroDePedidos.repositories.ClientRepository;
import br.com.brunogambim.CRUDCadastroDePedidos.resources.exception.FieldMessage;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, UpdateClientDTO>{
	
	private ClientRepository clientRepository;
	private HttpServletRequest request;
	
	@Autowired
	public ClientUpdateValidator(ClientRepository clientRepository,HttpServletRequest request) {
		this.clientRepository = clientRepository;
		this.request = request;
	}

	@Override
	public void initialize(ClientUpdate constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(UpdateClientDTO value, ConstraintValidatorContext context) {
		List<FieldMessage> fieldMesssageList = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		Map<String, String> atributes = (Map<String, String>) this.request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long id = Long.parseLong(atributes.get("id"));
	
		Optional<Client> client = this.clientRepository.findByEmail(value.getEmail());
		if(!client.isEmpty() && !client.get().getId().equals(id)) {
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
