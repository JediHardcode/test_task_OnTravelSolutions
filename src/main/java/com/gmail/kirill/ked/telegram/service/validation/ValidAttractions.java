package com.gmail.kirill.ked.telegram.service.validation;

import com.gmail.kirill.ked.telegram.service.validation.contraint.ValidAttractionsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidAttractionsValidator.class)
public @interface ValidAttractions {
    String message() default "attractions names are null or empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}