package com.board.game.mankala.exception;

import com.board.game.mankala.dto.RestResponse;
import com.board.game.mankala.dto.RestResponseType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {MancalaBoardNotFoundException.class})
    protected ResponseEntity<RestResponse> handleNotFoundException(MancalaBoardNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestResponse(RestResponseType.ERROR, "Board not found"));
    }

    @ExceptionHandler(value = {MankalaOutOfBandException.class})
    protected ResponseEntity<RestResponse> handleOutOfBandException(MankalaOutOfBandException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestResponse(RestResponseType.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(value = {MankalaIsTheWrongTurnException.class})
    protected ResponseEntity<RestResponse> handleMakeTheWrongTurn(MankalaIsTheWrongTurnException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new RestResponse(RestResponseType.WARNING, ex.getMessage()));
    }

    @ExceptionHandler(value = {MankalaWebException.class})
    protected ResponseEntity<RestResponse> handleWebException(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestResponse(RestResponseType.WARNING, ex.getMessage()));
    }
}
