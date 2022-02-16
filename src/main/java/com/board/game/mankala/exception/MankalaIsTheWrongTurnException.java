package com.board.game.mankala.exception;

public class MankalaIsTheWrongTurnException extends RuntimeException {
    public MankalaIsTheWrongTurnException(String message) {
        super(message);
    }
}