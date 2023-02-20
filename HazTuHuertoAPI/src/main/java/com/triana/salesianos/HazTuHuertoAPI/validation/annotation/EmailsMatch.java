package com.triana.salesianos.HazTuHuertoAPI.validation.annotation;

import com.triana.salesianos.HazTuHuertoAPI.validation.validator.EmailMatchValidator;
import com.triana.salesianos.HazTuHuertoAPI.validation.validator.PasswordsMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailMatchValidator.class)
@Documented
public @interface EmailsMatch {

    String message() default "Los emails introducidas no coinciden";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};

    String passwordField();
    String verifyPasswordField();

}

