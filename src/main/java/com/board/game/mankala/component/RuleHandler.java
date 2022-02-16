package com.board.game.mankala.component;

import com.board.game.mankala.data.Board;
import com.board.game.mankala.enumeration.PlayerType;

public interface RuleHandler {
    void switchPlayer(Board board, int pitId, PlayerType type);

    void getExtra(Board board, int pitId , PlayerType type);

    boolean isTheEndOfTheGame(Board board);
}
