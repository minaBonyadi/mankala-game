package com.board.game.mankala.component;


import com.board.game.mankala.data.Board;
import com.board.game.mankala.data.BotPlayerBoard;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.enumeration.StrategyName;
import com.board.game.mankala.exception.KalahaException;
import com.board.game.mankala.impl.PlayingStrategy;
import org.apache.commons.lang3.RandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealToBotPlayingStrategy implements PlayingStrategy {

    private int BOT_RANDOM_PIT_ID = RandomUtils.nextInt(1, 6); //TODO
    private final RulesManagementImpl ruleHandler;
    private static final int ZERO = 0;

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.REALTOBOT;
    }

    public Board startPlayByRealPlayer(Board board, int pitId) {
        int pitValue = board.getRealPits().get(pitId);

        if (!isChosenPitFill(board, pitId)) {
            throw new KalahaException(""); //ToDo add exception
        }

        board.getRealPits().put(pitId, ZERO);

//        RealPlayerBoard boardAfterRealPlayer = getBoardAfterRealPlayerMove(board, pitId, pitValue);
//        BotPlayerBoard boardAfterBotPlayer = getBoardAfterBotPlayerMove(board, BOT_RANDOM_PIT_ID);
//
//        Board.builder().botPits()
        return board;
    }

    private boolean isChosenPitFill(Board board, int pitId) {
        int pitValue = board.getRealPits().get(pitId);

        if (pitValue == ZERO) {
            playAgain(board, PlayerType.REAL, pitValue, pitId);
//            return adviser
        }
        return true;
    }

    private Board getBoardAfterRealPlayerMove(Board board, int pitId, int pitValue) {
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
                board.setRealStorage(board.getRealStorage() + 1);
                pitValue--;
                playAgain(board, PlayerType.REAL, pitValue, pitId);
                if (pitValue > ZERO) {
                    ruleHandler.switchToBotPlayer(board, pitValue);
                    break;
                }
            }
        }
        return Board.builder()
                .realPits(board.getRealPits())
                .realStorage(board.getRealStorage())
                .build();
    }

    private int getRealPlayerPreviousPitValue(Board board, int pitId) {
        if (pitId <= board.getRealPits().size()) {
            return board.getRealPits().get(pitId);
        }
        throw new KalahaException(String.format("Can not find the previous value of this pit {%s}", pitId));
    }

    private BotPlayerBoard getBoardAfterBotPlayerMove(Board board, int botPitId) {
        int value = board.getBotPits().get(botPitId);
        board.getBotPits().put(botPitId, ZERO);

        return divideBotPlayerValues(board, botPitId, value);
    }

    private BotPlayerBoard divideBotPlayerValues(Board board, int pitId, int pitValue){
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
                board.setBotStorage(board.getBotStorage() + 1);
                pitValue--;
                playAgain(board, PlayerType.BOT, pitValue, pitId);
                if (pitValue > ZERO) {
                    ruleHandler.switchToRealPlayer(board, pitValue);
                    break;
                }
            }
        }
        return BotPlayerBoard.builder()
                .botPits(board.getBotPits())
                .botStorage(board.getBotStorage())
                .build();
    }

    private int getBotPreviousValue(Board board, int pitId) {
        if (pitId < board.getBotPits().size()) {
            return board.getBotPits().get(pitId + 1);
        } else {
            return board.getBotPits().get(pitId);
        }
    }

    private void playAgain(Board board, PlayerType type, int pitValue, int pitId) {
        if (pitValue == ZERO && !ruleHandler.isTheEndOfTheGame(board)) {
            if (type.equals(PlayerType.BOT)) {
                getBoardAfterBotPlayerMove(board, BOT_RANDOM_PIT_ID);
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
