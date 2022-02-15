package com.board.game.mankala.component;

import com.board.game.mankala.config.MankalaPropertiesConfiguration;
import com.board.game.mankala.entity.Board;
import com.board.game.mankala.repository.BoardRepository;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.handler.SowHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BotPlayerImpl implements SowHandler {

    private final RulesImpl ruleHandler;
    private final MankalaPropertiesConfiguration kalahaSetting;
    private final BoardRepository boardRepository;

    /**
     *
     * @param board
     * @param pitId
     * @param pitValue
     * @param type
     */
    @Override
    public void sow(Board board, int pitId, int pitValue, PlayerType type) {
        board.getBotPits().put(pitId, kalahaSetting.getZero());

        int previousValueOfCurrentIndex = kalahaSetting.getZero();

        while (pitValue > kalahaSetting.getZero()) {

            if (pitId > kalahaSetting.getPitsIdMinSize() && pitId <= board.getBotPits().size()) {
                pitId--;
                previousValueOfCurrentIndex = getBotPreviousValue(board, pitId);
                board.getBotPits().put(pitId, board.getBotPits().get(pitId) + 1);
                pitValue--;
            }

            if (previousValueOfCurrentIndex == kalahaSetting.getZero() &&
                    pitValue == kalahaSetting.getZero()) {
                ruleHandler.getExtra(board, pitId++, PlayerType.BOT);
            }

            if (pitId == kalahaSetting.getPitsIdMinSize() && pitValue > kalahaSetting.getZero()) {
                board.setBotStorage(board.getBotStorage() + 1);
                pitValue--;
                isBotTurn(board, pitValue);
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

    private void isBotTurn(Board board, int pitValue) {
        if (pitValue == kalahaSetting.getZero()) {
            board.setRealTurn(false);
            board.setBotTurn(true); // it is bot player turning again
        }else {
            board.setRealTurn(true);
        }
        boardRepository.save(board);
    }

}
