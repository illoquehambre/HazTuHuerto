package com.triana.salesianos.HazTuHuertoAPI.security.jwt.refresh;

import com.triana.salesianos.HazTuHuertoAPI.security.errorhandling.JwtTokenException;

public class RefreshTokenException extends JwtTokenException {

    public RefreshTokenException(String msg) {
        super(msg);
    }

}


