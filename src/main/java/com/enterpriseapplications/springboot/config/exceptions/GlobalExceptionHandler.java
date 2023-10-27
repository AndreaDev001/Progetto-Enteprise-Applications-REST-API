package com.enterpriseapplications.springboot.config.exceptions;


import com.enterpriseapplications.springboot.config.internalization.MessageGetter;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageGetter messageGetter;

    @ExceptionHandler({NoSuchElementException.class,MissingItem.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> missingItemException(NoSuchElementException exception, HttpServletRequest request) {
        return errorResponse(HttpStatus.NOT_FOUND,Date.from(Instant.now()),"error.http.notFound",request.getRequestURI());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class,
            MissingRequestValueException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageConversionException.class,
            IllegalArgumentException.class,
            DataAccessException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> badRequestException(Exception exception,HttpServletRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST,Date.from(Instant.now()),"error.http.badRequest", request.getRequestURI());
    }

    @ExceptionHandler({InvalidFormat.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> invalidFormatException(InvalidFormat invalidFormat, HttpServletRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST,Date.from(Instant.now()),invalidFormat.getMessage(),request.getRequestURI());
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<BindErrorResponse> bindException(BindException exception,HttpServletRequest request) {
        Map<String,String> results = new HashMap<>();
        List<FieldError> errors = exception.getFieldErrors();
        for(FieldError error: errors)
            results.put(error.getField(),error.getDefaultMessage());
        return bindErrorResponse(HttpStatus.BAD_REQUEST,Date.from(Instant.now()),request.getRequestURI(),results);
    }

    @ExceptionHandler({BannedException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> bannedException(BannedException exception,HttpServletRequest request) {
        return errorResponse(HttpStatus.UNAUTHORIZED,Date.from(Instant.now()),"error.bans.banned",request.getRequestURI());
    }

    @ExceptionHandler({HttpClientErrorException.class})
    @ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
    public ResponseEntity<ErrorResponse> tooManyRequestException(HttpClientErrorException exception,HttpServletRequest request) {
        return errorResponse(HttpStatus.TOO_MANY_REQUESTS,Date.from(Instant.now()),"error.http.tooManyRequests",request.getRequestURI());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> serverError(HttpServerErrorException serverErrorException,HttpServletRequest request) {
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,Date.from(Instant.now()),"error.http.internalServerError",request.getRequestURI());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> conflictError(DataIntegrityViolationException dataIntegrityViolationException,HttpServletRequest request) {
        return errorResponse(HttpStatus.CONFLICT,Date.from(Instant.now()),"error.http.conflict",request.getRequestURI());
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> unAuthorized(AuthenticationException authenticationException,HttpServletRequest httpServletRequest) {
        return errorResponse(HttpStatus.UNAUTHORIZED,Date.from(Instant.now()),"error.http.unauthorized",httpServletRequest.getRequestURI());
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> forbiddenError(AccessDeniedException accessDeniedException,HttpServletRequest request) {
        return errorResponse(HttpStatus.FORBIDDEN,Date.from(Instant.now()),"error.http.forbidden",request.getRequestURI());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<ErrorResponse> methodNotAllowedError(HttpRequestMethodNotSupportedException exception,HttpServletRequest request) {
        return errorResponse(HttpStatus.METHOD_NOT_ALLOWED,Date.from(Instant.now()),"error.http.unsupported",request.getRequestURI());
    }

    @ExceptionHandler({MultipartException.class,MaxUploadSizeExceededException.class})
    @ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
    public ResponseEntity<ErrorResponse> maxUploadSizeException(MaxUploadSizeExceededException exception,HttpServletRequest request) {
        return errorResponse(HttpStatus.PAYLOAD_TOO_LARGE,Date.from(Instant.now()),"error.http.maxUpload",request.getRequestURI());
    }

    public ResponseEntity<ErrorResponse> errorResponse(HttpStatus httpStatus, Date date, String code, String url) {
        ErrorResponse errorResponse = new ErrorResponse(date,url,String.valueOf(httpStatus.value()),messageGetter.getMessage(code));
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    public ResponseEntity<BindErrorResponse> bindErrorResponse(HttpStatus httpStatus,Date date,String url,Map<String,String> errors) {
        BindErrorResponse bindErrorResponse = new BindErrorResponse(date,url,messageGetter.getMessage("error.http.invalidData"),String.valueOf(httpStatus.value()),errors);
        return ResponseEntity.status(httpStatus).body(bindErrorResponse);
    }
}
