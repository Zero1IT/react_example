package com.gstu.pda.exceptions;

import com.gstu.pda.exceptions.models.RestError;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * createdAt: 10/10/2020
 * project: One
 *
 * @author Alex
 */
@RestControllerAdvice
public class RestExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestError> invalidRequestBodyHandler(MethodArgumentNotValidException e) {
        final RestError error = new RestError();
        final BindingResult bindingResult = e.getBindingResult();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            error.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity
                .badRequest()
                .body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestError> invalidEntity(ConstraintViolationException e) {
        final Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        final RestError error = new RestError();
        for (ConstraintViolation<?> cv : constraintViolations) {
            error.addError(cv.getPropertyPath().toString(), cv.getMessage());
        }
        return ResponseEntity
                .badRequest()
                .body(error);
    }
}
