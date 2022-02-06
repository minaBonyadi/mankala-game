package com.board.game.mankala.component;

import com.board.game.mankala.data.Board;
import com.board.game.mankala.enumeration.PlayerType;
import org.springframework.stereotype.Component;


@Component
public class RulesManagementImpl implements RuleHandler {

    @Override
    public void makeTurn(Board board, int pitId, PlayerType playerType) {
        if (playerType == PlayerType.BOT){
            makeTurnToBotPlayer(board, pitId);
        }
        makeTurnToRealPlayer(board, pitId);
    }

    @Override
    public void getExtra(Board board, int pitId, PlayerType type) {

    }

    @Override
    public boolean isTheEndOfTheGame(Board board) {
//        if (realPlayer.values().stream().allMatch(value -> value == RealToBotPlaying.ZERO) ||
//                botPlayer.values().stream().allMatch(value -> value == RealToBotPlaying.ZERO)) {
//
//            RealToBotPlaying.realStorage += RealToBotPlaying.realPlayer.values().stream().mapToInt(Integer::intValue).sum();
//            RealToBotPlaying.realPlayer.replaceAll((k, v) -> v = RealToBotPlaying.ZERO);
//            RealToBotPlaying.botStorage += botPlayer.values().stream().mapToInt(Integer::intValue).sum();
//            RealToBotPlaying.botPlayer.replaceAll((k, v) -> v = RealToBotPlaying.ZERO);
//            return true;
//        }
        return false;
    }


    public void makeTurnToBotPlayer(Board board, int pitId){
//        int firstIndex = 1;
//        while(pitId > 0){
//            RealToBotPlaying.botPlayer.put(firstIndex,(botPlayer.get(firstIndex)) + 1);
//            firstIndex++; pitId--;
//            if (firstIndex > botPlayer.size() && pitId > RealToBotPlaying.ZERO){
//                RealToBotPlaying.botStorage++; pitId--;
//                switchToRealPlayer(realPlayer, botPlayer, realStorage, botStorage, pitId);
//                break;
//            }
//        }
    }

    public void makeTurnToRealPlayer(Board board, int pitValue){
//        int index = 1;
//        while(value > RealToBotPlaying.ZERO){
//            RealToBotPlaying.realPlayer.put(index,(realPlayer.get(index)) + 1);
//            index++; value--;
//            if (index > realPlayer.size() && value > RealToBotPlaying.ZERO){
//                RealToBotPlaying.realStorage++; value--;
//                switchToBotPlayer(botPlayer, realPlayer, botStorage, realStorage, value);
//                break;
//            }
//        }
    }
}
