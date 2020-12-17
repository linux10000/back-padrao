package br.com.juliano.appclient.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.com.juliano.appclient.structure.exception.JulianoException;
import br.com.juliano.appclient.structure.exception.JulianoRuntimeException;
import br.com.juliano.appclient.structure.utils.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ControllerAdvice
public class GlobalExceptionController {
	
	@Autowired
	private MessageUtil messageUtil;

	@ExceptionHandler({Throwable.class})
	public ResponseEntity<ErrorResponse> handleDefaultException(Throwable ex, WebRequest request) {
		ex.printStackTrace();
		
		return new ResponseEntity<ErrorResponse>(
				ErrorResponse.builder()
							.message(ex.getMessage())
							.exception(ex.getClass().getSimpleName())
							.build(),
					HttpStatus.INTERNAL_SERVER_ERROR
				);
	}
	
	@ExceptionHandler({JulianoException.class, JulianoRuntimeException.class})
	public ResponseEntity<ErrorResponse> handleDefaultHcfException(Exception ex, WebRequest request) {
		ex.printStackTrace();
		
		return new ResponseEntity<ErrorResponse>(
				ErrorResponse.builder()
							.message(ex.getMessage())
							.exception(ex.getClass().getSimpleName())
							.build(), 
				HttpStatus.BAD_REQUEST
				);
	}
	
	@ExceptionHandler(value = { MethodArgumentNotValidException.class, BindException.class })
	public ResponseEntity<ErrorResponse> handleValidationError(BindException ex) {
		Function<ObjectError, CampoInvalido> toCampoInvalido = obj -> {
			boolean isCampo = obj instanceof FieldError;
			return new CampoInvalido((isCampo ? ((FieldError) obj).getField() : obj.getObjectName()), messageUtil.resolveMessage(obj.getDefaultMessage()), isCampo);
		};
		
		return new ResponseEntity<ErrorResponse>(
				ErrorResponse.builder()
							.message(ex.getMessage())
							.exception(ex.getClass().getSimpleName())
							.errors(
									Stream.concat(
										Optional.ofNullable(ex.getBindingResult().getGlobalErrors()).orElse(new ArrayList<>()).stream().map(toCampoInvalido), 
										Optional.ofNullable(ex.getBindingResult().getFieldErrors()).orElse(new ArrayList<>()).stream().map(toCampoInvalido)
										).collect(Collectors.toList())
									)
							.build(), 
				HttpStatus.BAD_REQUEST
				);
	}
	
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleValidationError(javax.validation.ConstraintViolationException ex) {
		Function<ConstraintViolation<?>, CampoInvalido> toCampoInvalido = obj -> {
			return new CampoInvalido((obj.getRootBeanClass().getName()), messageUtil.resolveMessage(obj.getMessage(), obj.getExecutableParameters(), Locale.getDefault()), false);
		};
		
		return new ResponseEntity<ErrorResponse>(
				ErrorResponse.builder()
							.message(ex.getMessage())
							.exception(ex.getClass().getSimpleName())
							.errors(ex.getConstraintViolations().stream().map(toCampoInvalido).collect(Collectors.toList()))
							.build(), 
				HttpStatus.BAD_REQUEST
				);
	}
	
	
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	static class CampoInvalido {
		private String name;
		private String message;
		private boolean campo;
	}
	
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	static class ErrorResponse {
		private String message;
		private String exception;
		private List<CampoInvalido> errors;
	}
}
