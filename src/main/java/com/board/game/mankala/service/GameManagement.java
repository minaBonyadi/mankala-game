package com.board.game.mankala.service;

import com.board.game.mankala.enumeration.StrategyName;
import com.board.game.mankala.impl.PlayingStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameManagement {
    private final GameStrategyFactory strategyFactory;

    public void play(){
        PlayingStrategy strategy = strategyFactory.findStrategy(StrategyName.REALTOBOT);
//        while (!commandHandler.stopTheGame()) {
//            strategy.handleTheGame();
//        }
    }
}
