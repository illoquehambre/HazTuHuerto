package com.triana.salesianos.HazTuHuertoAPI.exception;

import javax.naming.AuthenticationException;

public class NoMatchPasswordException extends AuthenticationException {

    public NoMatchPasswordException(){super(("The passwords doesn´t match"));}
}
