package com.triana.salesianos.HazTuHuertoAPI.exception;

import javax.naming.AuthenticationException;
import javax.validation.ValidationException;

public class NoMatchPasswordException extends ValidationException {

    public NoMatchPasswordException(){super(("The passwords doesn´t match"));}
}
