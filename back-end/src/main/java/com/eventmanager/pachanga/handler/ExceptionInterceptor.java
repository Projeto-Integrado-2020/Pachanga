package com.eventmanager.pachanga.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.eventmanager.pachanga.errors.ValidacaoException;

@ControllerAdvice
//@Slf4j
public class ExceptionInterceptor extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ValidacaoException.class)
	public ValidacaoException handleValidacaoException(ValidacaoException e){
		return e;
	}
	
}
