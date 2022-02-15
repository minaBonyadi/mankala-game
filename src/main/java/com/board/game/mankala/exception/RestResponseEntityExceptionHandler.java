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

    @ExceptionHandler(value = {KalahaBoardNotFoundException.class})
    protected ResponseEntity<RestResponse> handleNotFoundException(KalahaBoardNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestResponse(RestResponseType.ERROR, "Board not found"));
    }

    @ExceptionHandler(value = {KalahaOutOfBandException.class})
    protected ResponseEntity<RestResponse> handleOutOfBandException(KalahaOutOfBandException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestResponse(RestResponseType.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(value = {KalahaIsTheWrongTurnException.class})
    protected ResponseEntity<RestResponse> handleMakeTheWrongTurn(KalahaIsTheWrongTurnException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new RestResponse(RestResponseType.WARNING, ex.getMessage()));
    }

    @ExceptionHandler(value = {KalahaWebException.class})
    protected ResponseEntity<RestResponse> handleWebException(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestResponse(RestResponseType.WARNING, ex.getMessage()));
    }
}
