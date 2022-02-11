package com.board.game.mankala.component;

import com.board.game.mankala.config.KalahaPropertiesConfiguration;
import com.board.game.mankala.data.Board;
import com.board.game.mankala.data.BoardRepository;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.exception.KalahaWebException;
import com.board.game.mankala.handler.SowHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RealPlayerImpl implements SowHandler {

    private final KalahaPropertiesConfiguration kalahaSetting;
    private final RealToBotStrategyRulesImpl ruleHandler;
    private final BoardRepository boardRepository;

    @Override
    public void sow(Board board, int pitId, int pitValue, PlayerType type) {
        board.getRealPits().put(pitId, kalahaSetting.getZero());
        int nextValueOfCurrentIndex = kalahaSetting.getZero();

        while (pitValue > kalahaSetting.getZero()) {

            if (pitId > kalahaSetting.getZero() && pitId < board.getRealPits().size()) {
                pitId++;
                nextValueOfCurrentIndex = getRealPreviousValue(board, pitId);
                board.getRealPits().put(pitId, board.getRealPits().get(pitId) + 1);
                pitValue--;
            }

            if (nextValueOfCurrentIndex == kalahaSetting.getZero() &&
                    pitValue == kalahaSetting.getZero()) {
                ruleHandler.getExtra(board, pitId, PlayerType.REAL);
            }

            if (pitId == board.getRealPits().size() && pitValue > kalahaSetting.getZero()) {
                board.setRealStorage(board.getRealStorage() + 1);
                pitValue--;
                ruleHandler.playAgain(board, PlayerType.REAL, pitId, pitValue);
                if (pitValue > kalahaSetting.getZero()) {
                    ruleHandler.switchPlayer(board, pitValue, PlayerType.REAL); //choosen pit id should used instaed
                    break;
                }
            }
        }
        boardRepository.save(board);
    }

    private int getRealPreviousValue(Board board, int pitId) {
        if (pitId <= board.getRealPits().size()) {
            return board.getRealPits().get(pitId);
        }
        throw new KalahaWebException(String.format("Can not find the previous value of this pit {%s}", pitId));

    }
}
