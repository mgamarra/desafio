package br.com.challenge.core.base.security.authorization;

import java.lang.annotation.*;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Enforce {

//	/**
//	 * If true than enforces the present of a Team in the SecurityContext.
//	 * @return
//	 */
//	boolean team() default false;
//
//	/**
//	 * If true than enforces the present of a Project and a Team in the SecurityContext.
//	 * @return
//	 */
//	boolean project() default false;

}
