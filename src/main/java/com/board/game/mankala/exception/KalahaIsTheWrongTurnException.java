package com.board.game.mankala.exception;

public class KalahaIsTheWrongTurnException extends RuntimeException {
    public KalahaIsTheWrongTurnException(String message) {
        super(message);
    }
}