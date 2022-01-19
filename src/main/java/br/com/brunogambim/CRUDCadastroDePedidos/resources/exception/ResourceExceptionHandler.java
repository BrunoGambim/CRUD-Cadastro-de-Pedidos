package br.com.brunogambim.CRUDCadastroDePedidos.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.AuthorizationException;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.DataIntegrityException;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.FileException;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.InvalidURIParamsException;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException objectNotFoundException, 
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Not found",
				objectNotFoundException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standartError);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandartError> dataIntegrityViolation(DataIntegrityException dataIntegrityException,
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Data integrity",
				dataIntegrityException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standartError);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandartError> authorization(AuthorizationException authorizationException, HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Unauthorized",
				authorizationException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(standartError);
	}
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandartError> fileError(FileException fileException, HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "File error",
				fileException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standartError);
	}
	
	@ExceptionHandler(InvalidURIParamsException.class)
	public ResponseEntity<StandartError> invalidURIParams(InvalidURIParamsException invalidURIParamsException, 
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Parâmetros da requisição inválidos",
				invalidURIParamsException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standartError);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandartError> invalidRequestData(MethodArgumentNotValidException methodArgumentNotValidException, 
			HttpServletRequest httpServletRequest){
		ValidationError validationError = new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Invalid request data",
				methodArgumentNotValidException.getMessage(), httpServletRequest.getRequestURI());
		methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach((error)->{
			validationError.addField(new FieldMessage(error.getField(),error.getDefaultMessage()));
		});
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validationError);
	}
}
