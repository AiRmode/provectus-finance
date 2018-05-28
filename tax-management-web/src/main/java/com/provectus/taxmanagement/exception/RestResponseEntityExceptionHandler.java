package com.provectus.taxmanagement.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by alexey on 19.03.17.
 */
@ControllerAdvice
@EnableWebMvc
public class RestResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @ResponseBody
    @ExceptionHandler
    public ResponseEntity<?> handleConflict(DataIntegrityViolationException e) {
        return response(HttpStatus.CONFLICT, 40901, "Operation cannot be performed. Integrity Constraint violated", e.getRootCause().getMessage(), "");
    }

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleInconsistentEditException(IllegalAccessException e){
        return response(HttpStatus.CONFLICT, 40902, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<?> handleOptimisticLockingFailureException(IllegalAccessException e){
        return response(HttpStatus.CONFLICT, 40902, e.getMessage());
    }

    private ResponseEntity<String> response(HttpStatus status, int code, String msg) {
        return response(status, code, msg, "devMsg", "moreInfo");
    }

    private ResponseEntity<String> response(HttpStatus status, int code, String msg, String devMsg, String moreInfo) {
        return new ResponseEntity<String>(status.value() + code + msg + devMsg + moreInfo, status);
    }
}
