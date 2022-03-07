package com.dgtt.core.exception;

import java.io.Serializable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author DELL
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ExpiredResourcesDateException extends Exception implements Serializable {


  private static final long serialVersionUID = 2141685067515932795L;

  public ExpiredResourcesDateException(final String message) {
    super(message);
  }
}
