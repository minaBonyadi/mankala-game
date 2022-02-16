package com.board.game.mankala.handler;

import com.board.game.mankala.entity.Board;
import com.board.game.mankala.enumeration.PlayerType;

public interface SowHandler {
    Board sow(Board board, int pitId, int stone, PlayerType type);
}
