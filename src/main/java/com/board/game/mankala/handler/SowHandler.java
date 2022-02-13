package com.board.game.mankala.handler;

import com.board.game.mankala.entity.Board;
import com.board.game.mankala.enumeration.PlayerType;

public interface SowHandler {
    void sow(Board board, int pitId, int pitValue, PlayerType type);
}
