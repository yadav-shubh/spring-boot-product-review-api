package com.miniproject.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.miniproject.dto.GlobalResponseFormat;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RecordNotFoundException.class)
	protected ResponseEntity<GlobalResponseFormat<String>> handleRecordNotFound(RecordNotFoundException exception,
			WebRequest request) {
		GlobalResponseFormat<String> response = new GlobalResponseFormat<>();
		response.setError(true);
		response.setMessage(exception.getMessage());
		response.setData(null);
		return new ResponseEntity<GlobalResponseFormat<String>>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EntityNotExistException.class)
	protected ResponseEntity<GlobalResponseFormat<String>> handleEntityNotExist(EntityNotExistException exception,
			WebRequest request) {
		GlobalResponseFormat<String> response = new GlobalResponseFormat<>();
		response.setError(true);
		response.setMessage(exception.getMessage());
		response.setData(null);
		return new ResponseEntity<GlobalResponseFormat<String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { InvalidInputException.class })
	protected ResponseEntity<GlobalResponseFormat<String>> handleEntityNotExist(InvalidInputException exception,
			WebRequest request) {
		GlobalResponseFormat<String> response = new GlobalResponseFormat<>();
		response.setError(true);
		response.setMessage(exception.getMessage());
		response.setData(null);
		return new ResponseEntity<GlobalResponseFormat<String>>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CustomBeanValidationErrors> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		CustomBeanValidationErrors response = new CustomBeanValidationErrors();
		response.setError(true);
		response.setMessage("Validation Error");
		response.setErrorDetails(errors);
		return new ResponseEntity<CustomBeanValidationErrors>(response, HttpStatus.BAD_REQUEST);
	}


//	@ExceptionHandler(value = { EmptyResultDataAccessException.class, IllegalArgumentException.class })
//	protected ResponseEntity<GlobalResponseFormat<String>> handleIllegalArgumentException(
//			IllegalArgumentException exception, WebRequest request) {
//		GlobalResponseFormat<String> response = new GlobalResponseFormat<>();
//		response.setError(true);
//		response.setMessage(exception.getMessage());
//		response.setData(null);
//		return new ResponseEntity<GlobalResponseFormat<String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
}
