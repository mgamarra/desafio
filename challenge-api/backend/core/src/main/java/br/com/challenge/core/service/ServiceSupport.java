package br.com.challenge.core.service;

import org.springframework.aop.framework.AopContext;

@SuppressWarnings("ALL")
public abstract class ServiceSupport {

	protected <T> T proxy(T ref) {
		if (this != ref) throw new IllegalStateException("'ref' must be equal 'this'");
		return (T) AopContext.currentProxy();
	}
}
