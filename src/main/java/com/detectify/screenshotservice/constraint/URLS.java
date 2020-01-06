package com.detectify.screenshotservice.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = URLSValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface URLS {

    String message() default "URLS is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
