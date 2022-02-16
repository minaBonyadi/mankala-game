package com.board.game.mankala.component;

import com.board.game.mankala.entity.Board;
import com.board.game.mankala.repository.BoardRepository;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.handler.RuleHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class RulesImpl implements RuleHandler{

    private final BoardRepository boardRepository;

    /**
     *
     * @param board get all board data
     * @param remainingStone remaining stone count to switch
     * @param playerType REAL or BOT
     */
    @Override
    public void switchPlayer(Board board, int remainingStone, PlayerType playerType) {
        if (playerType == PlayerType.BOT) {
            switchToRealPlayer(board, remainingStone);
        } else {
            switchToBotPlayer(board, remainingStone);
        }
    }

    /**
     *
     * @param board all board data
     * @param pitId selected pit id
     * @param type REAL or BOT
     */
    @Override
    public void getExtra(Board board, int pitId, PlayerType type) {
        if (type.equals(PlayerType.BOT) && pitId > 0 && board.getRealPits().get(pitId) != 0){
            board.setBotStorage(board.getBotStorage() + (board.getRealPits().get(pitId)) + 1);
            board.getBotPits().put(pitId, 0);
            board.getRealPits().put(pitId, 0);

        } else if (type.equals(PlayerType.REAL) && pitId > 0 && board.getBotPits().get(pitId) != 0){
            board.setRealStorage(board.getRealStorage() + (board.getBotPits().get(pitId)) + 1);
            board.getRealPits().put(pitId, 0);
            board.getBotPits().put(pitId, 0);
        }
        boardRepository.save(board);
    }

    /**
     *
     * @param board all board data
     * @param remainingStone remaining stone from real player side to switching to bot player side
     */
    private void switchToBotPlayer(Board board, int remainingStone){
        int pitId = 6;
        while (remainingStone > 0) {
            board.getBotPits().put(pitId, (board.getBotPits().get(pitId)) + 1);
            remainingStone--;
            pitId--;

            if (pitId < 1) {
                board.setBotStorage(board.getBotStorage() + 1);
                remainingStone--;
                switchToRealPlayer(board, remainingStone);
                break;
            }
        }
        boardRepository.save(board);
    }

    /**
     *
     * @param board all board data
     * @param remainingStone remaining stone from bot player side to switching to real player side
     */
    private void switchToRealPlayer(Board board, int remainingStone){
        int pitId = 1;
        while(remainingStone > 0) {
            board.getRealPits().put(pitId, (board.getRealPits().get(pitId)) + 1);
            pitId++;
            remainingStone--;

            if (pitId > board.getRealPits().size() && remainingStone > 0){
                board.setRealStorage(board.getRealStorage() + 1);
                remainingStone--;
                switchToBotPlayer(board, remainingStone);
                break;
            }
        }
        boardRepository.save(board);
    }
}
