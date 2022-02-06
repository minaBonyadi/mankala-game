package com.board.game.mankala.component;


import com.board.game.mankala.data.Board;
import com.board.game.mankala.data.BoardDto;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.enumeration.StrategyName;
import com.board.game.mankala.exception.KalahaException;
import com.board.game.mankala.impl.PlayingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealToBotPlayingStrategy implements PlayingStrategy {

    private final RulesManagementImpl ruleHandler;
    private static final int ZERO = 0;

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.REALTOBOT;
    }

    public void playBotRealPlayer(Board board, int pitId) {
        int pitValue = board.getRealPits().get(pitId);

        if (!isChosenPitFill(pitId, board)) {
            return; //ToDo add exception
        }

        board.getRealPits().put(pitId, ZERO);
        divideRealPlayerValues(board, pitValue);
//        isTheEndOfTheGame(board);
    }

    private boolean isChosenPitFill(int pitId, Board board) {
        int pitValue = board.getRealPits().get(pitId);

        if (pitValue == ZERO) {
            playAgain(PlayerType.REAL, pitValue);
//            return adviser
        }
        return true;
    }

    private void divideRealPlayerValues(Board board, int pitId) {
        int pitValue = board.getRealPits().get(pitId);
        int previousValueOfCurrentIndex = ZERO;

        while (pitValue > ZERO) {
            if (pitId > ZERO && pitId < board.getRealPits().size()) {
                pitId++;
                previousValueOfCurrentIndex = getRealPlayerPreviousPitValue(board, pitId);
                board.getRealPits().put(pitId, board.getRealPits().get(pitId) + 1);
                pitValue--;
            }
            if (previousValueOfCurrentIndex == ZERO &&
                    pitValue == ZERO) ruleHandler.getExtra(board, pitId, PlayerType.REAL);
            if (pitId == board.getRealPits().size() && pitValue > ZERO) {
                board.setBotPlayerStorage(board.getFirstPlayerStorage() + 1);
                pitValue--;
                playAgain(PlayerType.REAL, pitValue);
                if (pitValue > ZERO) {
                    ruleHandler.makeTurnToBotPlayer(board, pitValue);
                    break;
                }
            }
        }
    }

    private int getRealPlayerPreviousPitValue(Board board, int pitId) {
        if (pitId <= board.getRealPits().size()) {
            return board.getRealPits().get(pitId);
        }
        throw new KalahaException(String.format("Can not find the previous value of this pit {%s}", pitId));
    }

    private void playByBotPlayer(Board board, int index) {
        int value = board.getBotPits().get(index);
        board.getBotPits().put(index, ZERO);

        divideBotPlayerValues(board, index, value);
        isTheEndOfTheGame(board);
    }

    private void divideBotPlayerValues(Board board, int pitId, int pitValue){
        int previousValueOfCurrentIndex = ZERO;
        while (pitValue > ZERO) {
            if (pitId > ZERO && pitId < board.getBotPits().size()) {
                pitId++;
                previousValueOfCurrentIndex = getBotPreviousValue(board, pitId);
                board.getBotPits().put(pitId, board.getBotPits().get(pitId) + 1);
                pitValue--;
            }
            if (previousValueOfCurrentIndex == ZERO &&
                    pitValue == ZERO) ruleHandler.getExtra(board, pitId++, PlayerType.BOT);
            if (pitId == board.getBotPits().size() && pitValue > ZERO) {
                board.setBotPlayerStorage(board.getBotPlayerStorage() + 1);
                pitValue--;
                playAgain(PlayerType.BOT, pitValue);
                if (pitValue > ZERO) {
                    ruleHandler.makeTurnToRealPlayer(board, pitValue);
                    break;
                }
            }
        }
    }

    private int getBotPreviousValue(Board board, int pitId) {
        if (pitId < board.getBotPits().size()) {
            return board.getBotPits().get(pitId + 1);
        } else {
            return board.getBotPits().get(pitId);
        }
    }

    private void playAgain(PlayerType type, int value) {
//        if (value == ZERO && !ruleHandler.isTheEndOfTheGame(realPlayer, botPlayer)) {
//            commandHandler.printState(realPlayer, botPlayer, realStorage, botStorage);
//
//            if (type.equals(PlayerType.BOT)) {
//                playByBotPlayer(RandomUtils.nextInt(1, 6));
//            } else {
//                playBotRealPlayer(commandHandler.getInput());
//            }
//        }
    }

    private void isTheEndOfTheGame(Board board) {
//        if (ruleHandler.isTheEndOfTheGame(board)) {
//            commandHandler.whoWonTheGame(realStorage, botStorage);
//        }
    }
}
