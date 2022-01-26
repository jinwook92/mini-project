package com.mini.config;

import com.mini.api.dto.ApiResult;
import com.mini.exception.DataNotFoundException;
import com.mini.exception.ServiceRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@ControllerAdvice
public class GeneralExceptionHandler {

    private ResponseEntity<ApiResult> createResponse(Throwable throwable, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(new ApiResult(throwable, status), headers, status);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        return createResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            IllegalStateException.class, IllegalArgumentException.class,
            TypeMismatchException.class, HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class, MultipartException.class,
            JSONException.class
    })
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        return createResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<?> handleHttpMediaTypeException(Exception e) {
        return createResponse(e, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowedException(Exception e) {
        return createResponse(e, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity<?> handleServiceRuntimeException(ServiceRuntimeException e) {
        if (e instanceof DataNotFoundException) {
            return createResponse(e, HttpStatus.NOT_FOUND);
        }

        log.error(e.getMessage(), e);
        return createResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return createResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}