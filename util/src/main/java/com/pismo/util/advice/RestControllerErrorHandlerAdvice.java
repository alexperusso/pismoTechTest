package com.pismo.util.advice;

import com.pismo.util.exception.InvalidInputException;
import com.pismo.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestControllerErrorHandlerAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(RestControllerErrorHandlerAdvice.class);

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public HttpErrorInfo handleNotFoundExceptions(NotFoundException ex, WebRequest request) {
        return createHttpErrorInfo(NOT_FOUND, request, ex);
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    @ResponseBody
    public HttpErrorInfo handleInvalidInputException(InvalidInputException ex, WebRequest request) {
        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public HttpErrorInfo handleInvalidValueException(ValidationException ex, WebRequest request) {
        return createHttpErrorInfo(BAD_REQUEST, request, ex);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ValidationErrorInfo onMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        LOG.debug("Erro em Validacao de argumentos", e);
        ValidationErrorInfo error = new ValidationErrorInfo();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.addViolationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return error;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ValidationErrorInfo onConstraintValidationException(ConstraintViolationException e, WebRequest request) {
        LOG.debug("Erro em Validacao", e);
        ValidationErrorInfo error = new ValidationErrorInfo();
        //noinspection rawtypes
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.addViolationError(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return error;
    }

    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, WebRequest request, Exception ex) {
        final String path = request.getContextPath();
        final String message = ex.getMessage();

        LOG.error("Retornando Erro HTTP status: {} para o caminho: {}, mensagem: {}", httpStatus, path, message);
        return new HttpErrorInfo(httpStatus, path, message);
    }
}
