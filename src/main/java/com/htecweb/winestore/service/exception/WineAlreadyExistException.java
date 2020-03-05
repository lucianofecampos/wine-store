package com.htecweb.winestore.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WineAlreadyExistException extends BusinessException {

    public WineAlreadyExistException() {
        super("wines-5", HttpStatus.BAD_REQUEST);
    }
}
