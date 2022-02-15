package com.board.game.mankala.handler;

import com.board.game.mankala.entity.Board;
import com.board.game.mankala.enumeration.PlayerType;

public interface RuleHandler {

    void switchPlayer(Board board, int pitId, PlayerType type);

    void getExtra(Board board, int pitId , PlayerType type);
}
