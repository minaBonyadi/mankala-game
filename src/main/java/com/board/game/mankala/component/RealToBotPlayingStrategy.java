package com.board.game.mankala.component;

import com.board.game.mankala.config.KalahaPropertiesConfiguration;
import com.board.game.mankala.data.Board;
import com.board.game.mankala.data.BoardRepository;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.enumeration.StrategyName;
import com.board.game.mankala.exception.KalahaException;
import com.board.game.mankala.impl.PlayingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealToBotPlayingStrategy implements PlayingStrategy {

    private final KalahaPropertiesConfiguration kalahaSetting;
    private final RealToBotStrategyRulesImpl ruleHandler;
    private final BoardRepository boardRepository;

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.REALTOBOT;
    }

    public Board startPlayByRealPlayer(Board board, int pitId) {
        //TODO pitID shoudl not be null
        int chosenPitValue = board.getRealPits().get(pitId);

        if (!isChosenPitFill(board, pitId)) {
            throw new KalahaException("");
        }

        board.getRealPits().put(pitId, kalahaSetting.getZero());

        getBoardAfterRealPlayerMove(board, pitId, chosenPitValue);
        getBoardAfterBotPlayerMove(board, kalahaSetting.getBotRandomPitId());
        return boardRepository.findById(board.getId()).orElseThrow(() -> new KalahaException(""));
    }

    private boolean isChosenPitFill(Board board, int pitId) {
        int pitValue = board.getRealPits().get(pitId);

        if (pitValue == kalahaSetting.getZero()) {
            ruleHandler.playAgain(board, PlayerType.REAL, pitId, pitValue);
//            return adviser
        }
        return true;
    }

    private Board getBoardAfterRealPlayerMove(Board board, int pitId, int pitValue) {
        ruleHandler.sow(board, pitId, pitValue, PlayerType.REAL);
        return boardRepository.save(board);
    }

    private Board getBoardAfterBotPlayerMove(Board board, int pitId){
        int pitValue = board.getBotPits().get(pitId);
        ruleHandler.sow(board, pitId, pitValue, PlayerType.BOT);
        return boardRepository.save(board);
    }

}
