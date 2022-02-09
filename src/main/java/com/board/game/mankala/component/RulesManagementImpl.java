package com.board.game.mankala.component;

import com.board.game.mankala.data.Board;
import com.board.game.mankala.data.BoardRepository;
import com.board.game.mankala.enumeration.PlayerType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RulesManagementImpl implements RuleHandler {
    final BoardRepository boardRepository;

    @Override
    public void switchPlayer(Board board, int pitId, PlayerType playerType) {
        if (playerType == PlayerType.BOT){
            switchToRealPlayer(board, pitId);
        }
        switchToBotPlayer(board, pitId);
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
    public boolean isTheEndOfTheGame(Board board) {
        if (board.getRealPits().values().stream().allMatch(value -> value == 0) ||
                board.getBotPits().values().stream().allMatch(value -> value == 0)) {

            board.setRealStorage(board.getRealPits().values().stream().mapToInt(Integer::intValue).sum());
            board.getRealPits().replaceAll((k, v) -> v = 0);
            board.setBotStorage(board.getBotPits().values().stream().mapToInt(Integer::intValue).sum());
            board.getBotPits().replaceAll((k, v) -> v = 0);
            boardRepository.save(board);
            return true;
        }
        return false;
    }


    public void switchToBotPlayer(Board board, int pitId){
        int firstIndex = 1;
        while (pitId > 0) {
            board.getBotPits().put(firstIndex, (board.getBotPits().get(firstIndex)) + 1);
            firstIndex++;
            pitId--;

            if (firstIndex > board.getBotPits().size() && pitId > 0) {
                board.setBotStorage(board.getBotStorage() + 1);
                pitId--;
                switchToRealPlayer(board, pitId);
                break;
            }
        }
        boardRepository.save(board);
    }

    public void switchToRealPlayer(Board board, int pitValue){
        int pitId = 1;
        while(pitValue > 0){
            board.getRealPits().put(pitId, (board.getRealPits().get(pitId)) + 1);
            pitId++;
            pitValue--;

            if (pitId > board.getRealPits().size() && pitValue > 0){
                board.setRealStorage(board.getRealStorage() + 1);
                pitValue--;
                switchToBotPlayer(board, pitValue);
                break;
            }
        }
        boardRepository.save(board);
    }
}