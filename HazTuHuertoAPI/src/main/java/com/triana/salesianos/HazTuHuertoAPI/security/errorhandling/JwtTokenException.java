package com.triana.salesianos.HazTuHuertoAPI.security.errorhandling;

public class JwtTokenException extends RuntimeException{

    public JwtTokenException(String msg) {
        super(msg);
    }


}
