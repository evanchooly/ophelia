package controllers;

public class InvalidQueryException extends RuntimeException {
  public InvalidQueryException() {
  }

  public InvalidQueryException(String message) {
    super(message);
  }
}
