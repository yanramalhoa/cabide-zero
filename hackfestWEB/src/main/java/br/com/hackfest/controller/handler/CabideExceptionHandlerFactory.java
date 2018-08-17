package br.com.hackfest.controller.handler;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class CabideExceptionHandlerFactory extends ExceptionHandlerFactory {
	
	private ExceptionHandlerFactory parent;
	
	public CabideExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new CabideExceptionHandler(parent.getExceptionHandler());
	}
}