package com.board.game.mankala.component;

import com.board.game.mankala.config.MankalaPropertiesConfiguration;
import com.board.game.mankala.entity.Board;
import com.board.game.mankala.repository.BoardRepository;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.handler.RuleHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class RealToBotStrategyRulesImpl implements RuleHandler{

    private final BoardRepository boardRepository;
    private final MankalaPropertiesConfiguration kalahaSetting;

    @Override
    public void switchPlayer(Board board, int pitValue, PlayerType playerType) {
        if (playerType == PlayerType.BOT) {
            switchToRealPlayer(board, pitValue);
        } else {
            switchToBotPlayer(board, pitValue);
        }
    }

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

    @Override
    public void playAgain(Board board, PlayerType type, int pitId, int pitValue) {
//        if (pitValue == kalahaSetting.getZero()) {
//            if (type.equals(PlayerType.BOT)) {
//               currentGameStrategy.play(board, kalahaSetting.getBotRandomPitId(), PlayerType.BOT);
//            } else {
//                currentGameStrategy.play(board, pitId, PlayerType.REAL);
//            }
//        }
    }

    private void switchToBotPlayer(Board board, int remainingPitValue){
        int pitId = 6;
        while (remainingPitValue > 0) {
            board.getBotPits().put(pitId, (board.getBotPits().get(pitId)) + 1);
            remainingPitValue--;
            pitId--;

            if (pitId < 1) {
                board.setBotStorage(board.getBotStorage() + 1);
                remainingPitValue--;
                switchToRealPlayer(board, remainingPitValue);
                break;
            }
        }
        boardRepository.save(board);
    }

    private void switchToRealPlayer(Board board, int remainingPitValue){
        int pitId = 1;
        while(remainingPitValue > 0) {
            board.getRealPits().put(pitId, (board.getRealPits().get(pitId)) + 1);
            pitId++;
            remainingPitValue--;

            if (pitId > board.getRealPits().size() && remainingPitValue > 0){
                board.setRealStorage(board.getRealStorage() + 1);
                remainingPitValue--;
                switchToBotPlayer(board, remainingPitValue);
                break;
            }
        }
        boardRepository.save(board);
    }
}
