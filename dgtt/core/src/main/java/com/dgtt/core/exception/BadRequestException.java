/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgtt.core.exception;

import java.io.Serializable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author DELL
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    public BadRequestException(final String message) {
        super(message);
    }
}
