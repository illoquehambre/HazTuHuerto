package com.triana.salesianos.HazTuHuertoAPI.exception;

import javax.validation.ValidationException;

public class BannedAccountException extends RuntimeException {

    public BannedAccountException(){super(("The account you trying to Log in have been banned. For more information a llorarle a Luismi"));}
}
