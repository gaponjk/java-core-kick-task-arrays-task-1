package com.haponenka.app.exception;

public class CustomFileException extends CustomArrayException {

  public CustomFileException() {
    super();
  }

  public CustomFileException(String message) {
    super(message);
  }

  public CustomFileException(String message, Throwable cause) {
    super(message, cause);
  }

  public CustomFileException(Throwable cause) {
    super(cause);
  }
}
