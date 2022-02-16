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
    private final MankalaPropertiesConfiguration mankalaConfig;
    private final BoardRepository boardRepository;

    /**
     *
     * @param board with all data
     * @param pitId selected pit id of bot player which is random
     * @param stone selected pit stones
     * @param type BOT
     */
    @Override
    public Board sow(Board board, int pitId, int stone, PlayerType type) {
        board.getBotPits().put(pitId, mankalaConfig.getZero());

        int previousValueOfCurrentIndex = mankalaConfig.getZero();

        while (stone > mankalaConfig.getZero()) {

            if (pitId > mankalaConfig.getPitsIdMinSize() && pitId <= board.getBotPits().size()) {
                pitId--;
                previousValueOfCurrentIndex = getBotPreviousValue(board, pitId);
                board.getBotPits().put(pitId, board.getBotPits().get(pitId) + 1);
                stone--;
            }

            if (previousValueOfCurrentIndex == mankalaConfig.getZero() &&
                    stone == mankalaConfig.getZero()) {
                ruleHandler.getExtra(board, pitId++, PlayerType.BOT);
            }

            if (pitId == mankalaConfig.getPitsIdMinSize() && stone > mankalaConfig.getZero()) {
                board.setBotStorage(board.getBotStorage() + 1);
                stone--;
                isBotTurn(board, stone);
                if (stone > mankalaConfig.getZero()) {
                    ruleHandler.switchPlayer(board, stone, PlayerType.BOT);
                    break;
                }
            }
        }
        return boardRepository.save(board);
    }

    private int getBotPreviousValue(Board board, int pitId) {
        return board.getBotPits().get(pitId);
    }

    private void isBotTurn(Board board, int stone) {
        if (stone == mankalaConfig.getZero()) {
            board.setRealTurn(false);
            board.setBotTurn(true); // it is bot player turning again
        }else {
            board.setRealTurn(true);
        }
        boardRepository.save(board);
    }

}
