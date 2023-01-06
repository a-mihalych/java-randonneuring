package ru.mihalych.randonneuring.controller.Handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.mihalych.randonneuring.exception.NotFoundException;
import ru.mihalych.randonneuring.exception.RandonneuringException;
import ru.mihalych.randonneuring.exception.ValidationException;
import ru.mihalych.randonneuring.model.ErrorResponse;

@RestControllerAdvice("ru.mihalych.randonneuring.controller")
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException ex) {
        log.warn("!!! ErrorHandler: 404 {}", ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException ex) {
        log.warn("!!! ErrorHandler: 400 {}", ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRandonneuringException(final RandonneuringException ex) {
        log.warn("!!! ErrorHandler: 500 {}", ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }
}
