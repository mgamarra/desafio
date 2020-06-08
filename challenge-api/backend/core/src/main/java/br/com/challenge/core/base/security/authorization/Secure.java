package br.com.challenge.core.base.security.authorization;

import java.lang.annotation.*;

import br.com.challenge.core.data.enumeration.Role;
import org.springframework.core.annotation.AliasFor;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Secure {

	/**
	 * Verifies if user is an anonymous user when true.
	 * Note: If true no other verification is performed.
	 * Default: false
	 * @return
	 */
	boolean anonymous() default false;

	/**
	 * Verifies if user is authenticated when true.
	 * Default: true
	 * @return
	 */
	boolean authenticated() default true;

	/**
	 * Verifies if user is fully authenticated (not remember me) when true.
	 * Default: false
	 * @return
	 */
	boolean authenticatedFully() default false;

	/**
	 * Alias for hasRole
	 * @return
	 */
	@AliasFor("hasRole")
	Role[] value() default {};

	/**
	 * Verifies if user has all roles informed.
	 * @return
	 */
	@AliasFor("value")
	Role[] hasRole() default {};

	/**
	 * Verifies if user has any role informed.
	 * @return
	 */
	Role[] hasAnyRole() default {};

}
