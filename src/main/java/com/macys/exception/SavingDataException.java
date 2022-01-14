package com.macys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.macys.utils.ConsumerConstants;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)

public class SavingDataException extends RuntimeException {

    
	private static final long serialVersionUID = 1L;

	@Override
    public String toString() {
        return ConsumerConstants.ERROR_SAVING;
    }
}
