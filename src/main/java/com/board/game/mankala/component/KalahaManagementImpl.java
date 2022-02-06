package com.board.game.mankala.component;

import com.board.game.mankala.data.Board;
import com.board.game.mankala.data.BoardDto;
import com.board.game.mankala.enumeration.PlayerType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KalahaManagementImpl implements RuleHandler {

//    private void turnToRealPlayer(BoardDto board, int pitId, Board boardInfo, int value, int previousValue) {
//        while (value > 0) {
//            if (pitId < boardInfo.getRealPits().size()) {
//                pitId++;
//                previousValue = boardInfo.getBotPits().get(pitId);
//                boardInfo.getRealPits().put(pitId, boardInfo.getRealPits().get(pitId) + 1);
//            }
//            value--;
//            if (value == 0) {
//                getExtra(previousValue, pitId, PlayerType.BOT);
//            }
//            if (pitId == boardInfo.getRealPits().size()) {
//                board.setRealPlayerStorage(board.getRealPlayerStorage() + 1);
//                if (value == 0) {
//                    playAgain(PlayerType.REAL);
//                }
//                value--;
//                if (value > 0){
//                    botPlayerSwitch(value, PITS_MAX_LIMIT);
//                    break;
//                }
//            }
//        }
//    }

    @Override
    public void makeTurn(PlayerType playerType) {

//        turnToRealPlayer(BoardDto board, int pitId, Board boardInfo, int value, int previousValue)
    }

    @Override
    public void getExtra(Map<Integer, Integer> realPlayer, Map<Integer, Integer> botPlayer, int index, PlayerType type) {

    }

    @Override
    public boolean isTheEndOfTheGame(Map<Integer, Integer> realPlayer, Map<Integer, Integer> botPlayer) {
        return false;
    }
}
