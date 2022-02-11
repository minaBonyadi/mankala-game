package com.board.game.mankala.component;

import com.board.game.mankala.config.KalahaPropertiesConfiguration;
import com.board.game.mankala.data.Board;
import com.board.game.mankala.repository.BoardRepository;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.handler.SowHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BotPlayerImpl implements SowHandler {

    private final RealToBotStrategyRulesImpl ruleHandler;
    private final KalahaPropertiesConfiguration kalahaSetting;
    private final BoardRepository boardRepository;

    @Override
    public void sow(Board board, int pitId, int pitValue, PlayerType type) {
        board.getBotPits().put(pitId, kalahaSetting.getZero());

        int previousValueOfCurrentIndex = kalahaSetting.getZero();

        while (pitValue > kalahaSetting.getZero()) {

            if (pitId > 1 && pitId <= board.getBotPits().size()) {
                pitId--;
                previousValueOfCurrentIndex = getBotPreviousValue(board, pitId);
                board.getBotPits().put(pitId, board.getBotPits().get(pitId) + 1);
                pitValue--;
            }

            if (previousValueOfCurrentIndex == kalahaSetting.getZero() &&
                    pitValue == kalahaSetting.getZero()) {
                ruleHandler.getExtra(board, pitId++, PlayerType.BOT);
            }

            if (pitId == 1 && pitValue > kalahaSetting.getZero()) {
                board.setBotStorage(board.getBotStorage() + 1);
                pitValue--;
                ruleHandler.playAgain(board, PlayerType.BOT, pitId, pitValue);
                if (pitValue > kalahaSetting.getZero()) {
                    ruleHandler.switchPlayer(board, pitValue, PlayerType.BOT);
                    break;
                }
            }
        }
        boardRepository.save(board);
    }

    private int getBotPreviousValue(Board board, int pitId) {
        return board.getBotPits().get(pitId);
    }
}
