package com.triana.salesianos.HazTuHuertoAPI.validation.validator;

import com.triana.salesianos.HazTuHuertoAPI.validation.annotation.EmailsMatch;
import com.triana.salesianos.HazTuHuertoAPI.validation.annotation.PasswodsMatch;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailMatchValidator implements ConstraintValidator<EmailsMatch, Object> {

    private String passwordField;
    private String verifyPasswordField;

    @Override
    public void initialize(EmailsMatch constraintAnnotation) {
        passwordField = constraintAnnotation.passwordField();
        verifyPasswordField  = constraintAnnotation.verifyPasswordField();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String password = (String) PropertyAccessorFactory
                            .forBeanPropertyAccess(o)
                            .getPropertyValue(passwordField);

        String verifyEmail = (String) PropertyAccessorFactory
                .forBeanPropertyAccess(o)
                .getPropertyValue(verifyPasswordField);

        return StringUtils.hasText(password) && password.contentEquals(verifyEmail);
    }
}
