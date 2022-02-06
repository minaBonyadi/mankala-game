package com.board.game.mankala.component;

import com.board.game.mankala.enumeration.PlayerType;

import java.util.Map;

public interface RuleHandler {
    void makeTurn(PlayerType type);

    void getExtra(Map<Integer, Integer> realPlayer , Map<Integer, Integer> botPlayer, int index , PlayerType type);

    boolean isTheEndOfTheGame(Map<Integer, Integer> realPlayer ,Map<Integer, Integer> botPlayer);
}
