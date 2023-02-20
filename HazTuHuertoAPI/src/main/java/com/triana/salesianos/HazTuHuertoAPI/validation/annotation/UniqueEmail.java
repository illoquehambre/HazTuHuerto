package com.triana.salesianos.HazTuHuertoAPI.validation.annotation;

import com.triana.salesianos.HazTuHuertoAPI.validation.validator.UniqueEmailValidator;
import com.triana.salesianos.HazTuHuertoAPI.validation.validator.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
@Documented
public @interface UniqueEmail {

    String message() default "El email ya existe";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}


