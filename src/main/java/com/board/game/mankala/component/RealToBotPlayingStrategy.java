package com.board.game.mankala.component;

import com.board.game.mankala.config.KalahaPropertiesConfiguration;
import com.board.game.mankala.data.Board;
import com.board.game.mankala.data.BoardRepository;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.enumeration.StrategyName;
import com.board.game.mankala.exception.KalahaBoardNotFoundException;
import com.board.game.mankala.exception.KalahaWebException;
import com.board.game.mankala.handler.SowHandler;
import com.board.game.mankala.impl.PlayingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class RealToBotPlayingStrategy implements PlayingStrategy{

    private final BoardRepository boardRepository;
    private final RealPlayerImpl realPlayer;
    private final BotPlayerImpl botPlayer;
    private final KalahaPropertiesConfiguration kalahaSetting;
    private final RealToBotStrategyRulesImpl ruleHandler;

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.REALTOBOT;
    }

    public Board startPlayByRealPlayer(Board board, int pitId) {
        int chosenPitValue = board.getRealPits().get(pitId);

        if (chosenPitValue == kalahaSetting.getZero()) {
            throw new KalahaWebException("Chosen pit does not filled, please choose another pit!");
        }

        getBoardAfterRealPlayerMove(board, pitId, chosenPitValue);
        getBoardAfterBotPlayerMove(board, kalahaSetting.getBotRandomPitId());

        return boardRepository.findById(board.getId()).orElseThrow(KalahaBoardNotFoundException::new);
    }

    private void getBoardAfterRealPlayerMove(Board board, int pitId, int pitValue) {
        realPlayer.sow(board, pitId, pitValue, PlayerType.REAL);
    }

    private void getBoardAfterBotPlayerMove(Board board, int pitId){
        int pitValue = board.getBotPits().get(pitId);
        botPlayer.sow(board, pitId, pitValue, PlayerType.BOT);
    }

}
