package com.unfaithful.ecommerceapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomerRestExceptionHandler {

    // add exception handling code here

    @ExceptionHandler
    public ResponseEntity<CustomerErrorResponse> handleException(CustomerAlreadyExistException exc) {

        // create a StudentErrorResponse

        CustomerErrorResponse error = new CustomerErrorResponse();

        error.setStatus(HttpStatus.CONFLICT.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
