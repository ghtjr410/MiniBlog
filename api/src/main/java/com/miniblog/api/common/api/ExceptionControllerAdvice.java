package com.miniblog.api.common.api;

import com.miniblog.api.common.domain.exception.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<?> resourceNotFoundException(ResourceNotFoundException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(CONFLICT)
    @ExceptionHandler(ResourceDuplicateException.class)
    public ApiResponse<?> resourceDuplicateException(ResourceDuplicateException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(GONE)
    @ExceptionHandler(ResourceExpiredException.class)
    public ApiResponse<?> resourceExpiredException(ResourceExpiredException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ResourceInvalidException.class)
    public ApiResponse<?> resourceInvalidException(ResourceInvalidException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(ResourceUnauthorizedException.class)
    public ApiResponse<?> resourceUnAuthorizedException(ResourceUnauthorizedException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse("잘못된 요청입니다.");
        return ApiResponse.error(errorMessage);
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<?> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getMessage())
                .findFirst()
                .orElse("잘못된 요청입니다.");
        return ApiResponse.error(errorMessage);
    }
}
