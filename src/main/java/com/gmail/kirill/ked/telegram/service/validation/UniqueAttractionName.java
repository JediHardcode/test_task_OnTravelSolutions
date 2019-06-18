package com.gmail.kirill.ked.telegram.service.validation;

import com.gmail.kirill.ked.telegram.service.validation.contraint.UniqueAttractionNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueAttractionNameValidator.class)
public @interface UniqueAttractionName {
    String message() default "attraction with this name already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
