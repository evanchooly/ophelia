package controllers;

import org.jboss.netty.handler.timeout.ReadTimeoutException;

public class InvalidQueryException extends ReadTimeoutException {
  public InvalidQueryException() {
  }

  public InvalidQueryException(String message) {
    super(message);
  }
}
