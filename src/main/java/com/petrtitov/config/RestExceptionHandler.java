package com.petrtitov.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.petrtitov.config.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
@Slf4j
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorInfo dateParseError(IllegalArgumentException e) {
        return logAndGetErrorInfo(e, INPUT_ERROR);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class, ConstraintViolationException.class, BindException.class})
    public ErrorInfo validationError(Exception e) {
        return logAndGetErrorInfo(e, VALIDATION_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo internalError(Exception e) {
        return logAndGetErrorInfo(e, APP_ERROR);
    }

    private static ErrorInfo logAndGetErrorInfo(Exception e, ErrorType errorType) {
        List<String> errors = new ArrayList<>();
        if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            errors.addAll(bindException.getFieldErrors().stream()
                    .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                    .collect(Collectors.toList()));
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException validationException = (ConstraintViolationException) e;
            errors.addAll(validationException.getConstraintViolations().stream()
                    .map(fe -> String.format("[%s] %s", fe.getPropertyPath(), fe.getMessage()))
                    .collect(Collectors.toList()));
        } else {
            errors.add(e.getMessage());
        }

        return new ErrorInfo(errorType, errors);
    }
}