package org.example.springmvcnum2.Exeptions;

public class FileUploadException extends RuntimeException {
  public FileUploadException(String message) {
    super(message);
  }
}