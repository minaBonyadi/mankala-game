package com.board.game.mankala.component;

import com.board.game.mankala.config.MankalaPropertiesConfiguration;
import com.board.game.mankala.entity.Board;
import com.board.game.mankala.repository.BoardRepository;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.exception.MankalaWebException;
import com.board.game.mankala.handler.SowHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class RealPlayerImpl implements SowHandler {

    private final MankalaPropertiesConfiguration mankalaConfig;
    private final RulesImpl ruleHandler;
    private final BoardRepository boardRepository;

    /**
     *
     * @param board with all data
     * @param pitId selected pit id of bot player which is random
     * @param stone selected pit stones
     * @param type REAL
     */
    @Override
    public Board sow(Board board, int pitId, int stone, PlayerType type) {
        board.getRealPits().put(pitId, mankalaConfig.getZero());
        int nextValueOfCurrentIndex = mankalaConfig.getZero();

        while (stone > mankalaConfig.getZero()) {

            if (pitId > mankalaConfig.getZero() && pitId < board.getRealPits().size()) {
                pitId++;
                nextValueOfCurrentIndex = getRealPreviousValue(board, pitId);
                board.getRealPits().put(pitId, board.getRealPits().get(pitId) + 1);
                stone--;
            }

            if (nextValueOfCurrentIndex == mankalaConfig.getZero() &&
                    stone == mankalaConfig.getZero()) {
                ruleHandler.getExtra(board, pitId, PlayerType.REAL);
            }

            if (pitId == board.getRealPits().size() && stone > mankalaConfig.getZero()) {
                board.setRealStorage(board.getRealStorage() + 1);
                stone--;
                isRealTurnAgain(board, stone);
                if (stone > mankalaConfig.getZero()) {
                    ruleHandler.switchPlayer(board, stone, PlayerType.REAL); //choosen pit id should used instaed
                    break;
                }
            }
        }
        return boardRepository.save(board);
    }

    private int getRealPreviousValue(Board board, int pitId) {
        if (pitId <= board.getRealPits().size()) {
            return board.getRealPits().get(pitId);
        }
        log.error(String.format("Can not find the previous value of this pit {%s}", pitId));
        throw new MankalaWebException(String.format("Can not find the previous value of this pit {%s}", pitId));

    }

    private void isRealTurnAgain(Board board, int stone) {
        if (stone == mankalaConfig.getZero()) {
            board.setBotTurn(false);
            board.setRealTurn(true); // it is real player turning again
        }else {
            board.setBotTurn(true);
        }
        boardRepository.save(board);
    }
}
