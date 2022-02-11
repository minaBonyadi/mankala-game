package com.board.game.mankala.component;

import com.board.game.mankala.config.KalahaPropertiesConfiguration;
import com.board.game.mankala.data.Board;
import com.board.game.mankala.repository.BoardRepository;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.handler.RuleHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class RealToBotStrategyRulesImpl implements RuleHandler {

    final BoardRepository boardRepository;
    private final KalahaPropertiesConfiguration kalahaSetting;

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
        if (pitValue == kalahaSetting.getZero() && !isTheEndOfTheGame(board)) {
            if (type.equals(PlayerType.BOT)) {
//                sowBotPlayer(board, kalahaSetting.getBotRandomPitId(), pitValue);
            } else {
//                sowRealPlayer(board, pitId, pitValue);
            }
        }
    }

    @Override
    public boolean isTheEndOfTheGame(Board board) {
        if (board.getRealPits().values().stream().allMatch(value -> value == 0) ||
                board.getBotPits().values().stream().allMatch(value -> value == 0)) {

            board.setRealStorage(board.getRealStorage() + board.getRealPits().values().stream().mapToInt(Integer::intValue).sum());
            board.getRealPits().replaceAll((k, v) -> v = 0);
            board.setBotStorage(board.getBotStorage() + board.getBotPits().values().stream().mapToInt(Integer::intValue).sum());
            board.getBotPits().replaceAll((k, v) -> v = 0);
            boardRepository.save(board);
            return true;
        }
        return false;
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
