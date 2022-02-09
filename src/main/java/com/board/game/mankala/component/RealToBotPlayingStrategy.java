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
    private final RulesManagementImpl ruleHandler;
    private final BoardRepository boardRepository;

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.REALTOBOT;
    }

    public Board startPlayByRealPlayer(Board board, int pitId) {
        int pitValue = board.getRealPits().get(pitId);

        if (!isChosenPitFill(board, pitId)) {
            throw new KalahaException(""); //ToDo add exception
        }

        board.getRealPits().put(pitId, kalahaSetting.getZero());

        getBoardAfterRealPlayerMove(board, pitId, pitValue);
        getBoardAfterBotPlayerMove(board, kalahaSetting.getBotRandomPitId());
        return boardRepository.findById(board.getId()).orElseThrow(() -> new KalahaException(""));
    }

    private boolean isChosenPitFill(Board board, int pitId) {
        int pitValue = board.getRealPits().get(pitId);

        if (pitValue == kalahaSetting.getZero()) {
            playAgain(board, PlayerType.REAL, pitValue, pitId);
//            return adviser
        }
        return true;
    }

    private Board getBoardAfterRealPlayerMove(Board board, int pitId, int pitValue) {
        int previousValueOfCurrentIndex = kalahaSetting.getZero();

        while (pitValue > kalahaSetting.getZero()) {

            if (pitId > kalahaSetting.getZero() && pitId < board.getRealPits().size()) {
                pitId++;
                previousValueOfCurrentIndex = getRealPlayerPreviousPitValue(board, pitId);
                board.getRealPits().put(pitId, board.getRealPits().get(pitId) + 1);
                pitValue--;
            }

            if (previousValueOfCurrentIndex == kalahaSetting.getZero() &&
                    pitValue == kalahaSetting.getZero()) ruleHandler.getExtra(board, pitId, PlayerType.REAL);

            if (pitId == board.getRealPits().size() && pitValue > kalahaSetting.getZero()) {
                board.setRealStorage(board.getRealStorage() + 1);
                pitValue--;
                playAgain(board, PlayerType.REAL, pitValue, pitId);
                if (pitValue > kalahaSetting.getZero()) {
                    ruleHandler.switchPlayer(board, pitId, PlayerType.REAL);
                    break;
                }
            }
        }
        return boardRepository.save(board);
    }

    private int getRealPlayerPreviousPitValue(Board board, int pitId) {
        if (pitId <= board.getRealPits().size()) {
            return board.getRealPits().get(pitId);
        }
        throw new KalahaException(String.format("Can not find the previous value of this pit {%s}", pitId));
    }

    private Board getBoardAfterBotPlayerMove(Board board, int botPitId) {
        int value = board.getBotPits().get(botPitId);
        board.getBotPits().put(botPitId, kalahaSetting.getZero());

        return divideBotPlayerValues(board, botPitId, value);
    }

    private Board divideBotPlayerValues(Board board, int pitId, int pitValue){
        int previousValueOfCurrentIndex = kalahaSetting.getZero();
        while (pitValue > kalahaSetting.getZero()) {

            if (pitId > kalahaSetting.getZero() && pitId < board.getBotPits().size()) {
                pitId--;
                previousValueOfCurrentIndex = getBotPreviousValue(board, pitId);
                board.getBotPits().put(pitId, board.getBotPits().get(pitId) + 1);
                pitValue--;
            }

            if (previousValueOfCurrentIndex == kalahaSetting.getZero() &&
                    pitValue == kalahaSetting.getZero()){
                ruleHandler.getExtra(board, pitId++, PlayerType.BOT);
            }

            if (pitId == 1 && pitValue > kalahaSetting.getZero()) {
                board.setBotStorage(board.getBotStorage() + 1);
                pitValue--;
                playAgain(board, PlayerType.BOT, pitValue, pitId);
                if (pitValue > kalahaSetting.getZero()) {
                    ruleHandler.switchPlayer(board, pitId, PlayerType.BOT);
                    break;
                }
            }
        }
        return boardRepository.save(board);
    }

    private int getBotPreviousValue(Board board, int pitId) {
//        if (pitId < board.getBotPits().size()) {
//            return board.getBotPits().get(pitId - 1);
//        } else {
            return board.getBotPits().get(pitId);
//        }
    }

    private void playAgain(Board board, PlayerType type, int pitValue, int pitId) {
        if (pitValue == kalahaSetting.getZero() && !ruleHandler.isTheEndOfTheGame(board)) {
            if (type.equals(PlayerType.BOT)) {
                getBoardAfterBotPlayerMove(board, kalahaSetting.getBotRandomPitId());
            } else {
                startPlayByRealPlayer(board, pitId);
            }
        }
    }

    private Board isTheEndOfTheGame(Board board) {
        if (ruleHandler.isTheEndOfTheGame(board)) {
//            commandHandler.whoWonTheGame(realStorage, botStorage);
        }
        return board;
    }

}
