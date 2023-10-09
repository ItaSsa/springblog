package com.itainplace.springnblog.handlers;

import com.itainplace.springnblog.dto.CustomError;
import com.itainplace.springnblog.exceptions.PostNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Instant;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler{


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomError> resourceNotFound(IllegalArgumentException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now()
                , status.value()
                , e.getMessage()
                ,request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(PostNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now()
                , status.value()
                , e.getMessage()
                ,request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }


}
