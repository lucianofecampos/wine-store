package com.htecweb.beerstore.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BeerAlreadyExistException extends BusinessException {

    public BeerAlreadyExistException() {
        super("beers-5", HttpStatus.BAD_REQUEST);
    }
}
