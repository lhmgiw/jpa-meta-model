package com.lhmgiw.jpametamodel.validator;


import com.lhmgiw.jpametamodel.validator.impl.BlankValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BlankValidatorImpl.class)
public @interface BlankValidator {

    String message() default "Cannot Edit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
