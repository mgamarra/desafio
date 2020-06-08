package br.com.challenge.rest.util.exception;

import java.io.Serializable;

import javax.validation.ConstraintViolation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ViolationReport implements Serializable {

	private String rootClass, property, message;

	public ViolationReport() {
		super();
	}

	public ViolationReport(ConstraintViolation<?> cv) {
		this(cv.getRootBean().getClass().getSimpleName(), cv.getPropertyPath().toString(), cv.getMessage());
	}
}
