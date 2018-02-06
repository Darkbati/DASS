package com.liberty.dataserver.annotations;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({ TYPE, FIELD, METHOD })
@Retention(RUNTIME)
@SuppressWarnings("rawtypes")
public @interface ResourceDataSource {
	String name() default "";

	String lookup() default "";

	Class type() default java.lang.Object.class;

	enum AuthenticationType {
		CONTAINER, APPLICATION
	}

	AuthenticationType authenticationType() default AuthenticationType.CONTAINER;

	boolean shareable() default true;

	String mappedName() default "";

	String description() default "";
}
