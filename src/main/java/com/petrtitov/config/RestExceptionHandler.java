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

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static com.petrtitov.config.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
@Slf4j
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeParseException.class)
    public ErrorInfo dateParseError(DateTimeParseException e) {
        return logAndGetErrorInfo(e, DATA_ERROR);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class, BindException.class})
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
        if (e instanceof BindException bindException) {
            errors.addAll(bindException.getFieldErrors().stream()
                    .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                    .toList());
        } else errors.add(e.getMessage());

        return new ErrorInfo(errorType, errors);
    }
}