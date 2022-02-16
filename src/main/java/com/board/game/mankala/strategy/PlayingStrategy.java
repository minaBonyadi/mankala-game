package com.board.game.mankala.strategy;

import com.board.game.mankala.entity.Board;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.enumeration.StrategyName;

public interface PlayingStrategy {
    Board play(Board board, int pitId, PlayerType type);
    StrategyName getStrategyName();
}
