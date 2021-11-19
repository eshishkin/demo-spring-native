package org.eshishkin.edu.demospringnative.web;

import lombok.extern.slf4j.Slf4j;
import org.eshishkin.edu.demospringnative.exception.ResourceNotFoundException;
import org.eshishkin.edu.demospringnative.model.ServiceError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ServiceExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ServiceError onNonFoundException(ResourceNotFoundException ex) {
        return new ServiceError(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ServiceError onException(RuntimeException ex) {
        log.error("Unexpected exception happened", ex);
        return new ServiceError(ex.getMessage());
    }
}
