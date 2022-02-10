package com.board.game.mankala.component;

import com.board.game.mankala.data.Board;
import com.board.game.mankala.enumeration.PlayerType;

public interface RuleHandler {

    void sow(Board board, int pitId, int pitValue, PlayerType type);

    void switchPlayer(Board board, int pitId, PlayerType type);

    void getExtra(Board board, int pitId , PlayerType type);

    void playAgain(Board board, PlayerType type, int pitId, int pitValue);

    boolean isTheEndOfTheGame(Board board);

}
