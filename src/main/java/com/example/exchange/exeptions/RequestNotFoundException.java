package com.example.exchange.exeptions;

public class RequestNotFoundException extends RuntimeException {
   public RequestNotFoundException(String message) {
      super(message);
   }
}
