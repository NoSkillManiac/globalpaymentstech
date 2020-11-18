package auer.josh.globalpaymentstech.controller;

import auer.josh.globalpaymentstech.dto.Error;
import auer.josh.globalpaymentstech.exception.DeviceManagementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleDefaultException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DeviceManagementException.class)
    public ResponseEntity<List<Error>> handleDMException(DeviceManagementException ex) {
        return new ResponseEntity<>(ex.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
