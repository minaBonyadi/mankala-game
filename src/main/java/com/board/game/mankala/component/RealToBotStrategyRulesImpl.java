package com.board.game.mankala.component;

import com.board.game.mankala.config.KalahaPropertiesConfiguration;
import com.board.game.mankala.data.Board;
import com.board.game.mankala.data.BoardRepository;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.exception.KalahaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RealToBotStrategyRulesImpl implements RuleHandler {

    final BoardRepository boardRepository;
    private final KalahaPropertiesConfiguration kalahaSetting;

    @Override
    public void sow(Board board, int pitId, int pitValue, PlayerType type) {
        if (type == PlayerType.BOT) {
            sowBotPlayer(board, pitId, pitValue);
        } else {
            sowRealPlayer(board, pitId, pitValue);
        }
    }

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
                sowBotPlayer(board, kalahaSetting.getBotRandomPitId(), pitValue);
            } else {
                sowRealPlayer(board, pitId, pitValue);
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

    private void sowRealPlayer(Board board, int pitId, int pitValue) {
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
                getExtra(board, pitId, PlayerType.REAL);
            }

            if (pitId == board.getRealPits().size() && pitValue > kalahaSetting.getZero()) {
                board.setRealStorage(board.getRealStorage() + 1);
                pitValue--;
                playAgain(board, PlayerType.REAL, pitId, pitValue);
                if (pitValue > kalahaSetting.getZero()) {
                    switchPlayer(board, pitValue, PlayerType.REAL); //choosen pit id should used instaed
                    break;
                }
            }
        }
    }

    private void sowBotPlayer(Board board, int pitId, int pitValue) {
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
                    pitValue == kalahaSetting.getZero()){
                getExtra(board, pitId++, PlayerType.BOT);
            }

            if (pitId == 1 && pitValue > kalahaSetting.getZero()) {
                board.setBotStorage(board.getBotStorage() + 1);
                pitValue--;
                playAgain(board, PlayerType.BOT, pitId, pitValue);
                if (pitValue > kalahaSetting.getZero()) {
                    switchPlayer(board, pitValue, PlayerType.BOT);
                    break;
                }
            }
        }
    }

    private int getRealPreviousValue(Board board, int pitId) {
        if (pitId <= board.getRealPits().size()) {
            return board.getRealPits().get(pitId);
        }
        throw new KalahaException(String.format("Can not find the previous value of this pit {%s}", pitId));

    }
    private int getBotPreviousValue(Board board, int pitId) {
        return board.getBotPits().get(pitId);
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
