package com.miniproject.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.miniproject.dto.GlobalResponseFormat;

@ControllerAdvice
public class GlobalExceptionHandler {
	// handling global exception

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request) {

		GlobalResponseFormat errorDetails = new GlobalResponseFormat(true, exception.getMessage(), List.of());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
}
