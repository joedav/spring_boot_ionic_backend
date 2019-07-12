package com.joedav.cursomc.resources.exception;

import java.io.Serializable;

public class FieldMessage implements Serializable {
	private final static long serialVersionUID= 1L;
	
	//propriedades
	private String fieldName;
	private String message;
	
	// metodos consturtores
	public FieldMessage() {
	}

	public FieldMessage(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}
	
	// getters e setters
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
