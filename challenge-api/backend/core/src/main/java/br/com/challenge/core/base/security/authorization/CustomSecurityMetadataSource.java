package br.com.challenge.core.base.security.authorization;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.AbstractMethodSecurityMetadataSource;
import org.springframework.util.ClassUtils;

public class CustomSecurityMetadataSource extends AbstractMethodSecurityMetadataSource {

	@Override
	public Collection<ConfigAttribute> getAttributes(Method method, Class<?> targetClass) {
		if (method.getDeclaringClass() == Object.class) {
			return Collections.emptyList();
		}

		Enforce enforce = findAnnotation(method, targetClass, Enforce.class);
		Secure secure = findAnnotation(method, targetClass, Secure.class);

		if (enforce == null && secure == null) {
			return Collections.emptyList();
		}

		ArrayList<ConfigAttribute> attrs = new ArrayList<ConfigAttribute>(2);

		if (enforce != null) {
			attrs.add(new EnforceConfigAttribute(enforce));
		}

		if (secure != null) {
			attrs.add(new SecureConfigAttribute(secure));
		}

		attrs.trimToSize();

		return attrs;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	/**
	 * See
	 * {@link org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource#getAttributes(Method, Class)}
	 * for the logic of this method. The ordering here is slightly different in that we
	 * consider method-specific annotations on an interface before class-level ones.
	 */
	protected <A extends Annotation> A findAnnotation(Method method, Class<?> targetClass, Class<A> annotationClass) {
		// The method may be on an interface, but we need attributes from the target
		// class.
		// If the target class is null, the method will be unchanged.
		Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
		A annotation = AnnotationUtils.findAnnotation(specificMethod, annotationClass);

		if (annotation != null) {
			return annotation;
		}

		// Check the original (e.g. interface) method
		if (specificMethod != method) {
			annotation = AnnotationUtils.findAnnotation(method, annotationClass);

			if (annotation != null) {
				return annotation;
			}
		}

		// Check the class-level (note declaringClass, not targetClass, which may not
		// actually implement the method)
		annotation = AnnotationUtils.findAnnotation(specificMethod.getDeclaringClass(), annotationClass);

		if (annotation != null) {
			return annotation;
		}

		return null;
	}
}
