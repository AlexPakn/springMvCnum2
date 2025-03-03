package org.example.springmvcnum2.Exeptions;

public class StoreNotFoundException extends RuntimeException {
  public StoreNotFoundException(String message) {
    super(message);
  }
}