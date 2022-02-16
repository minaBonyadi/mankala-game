package com.board.game.mankala.component;

import com.board.game.mankala.entity.Board;
import com.board.game.mankala.enumeration.GameState;
import com.board.game.mankala.exception.MankalaBoardNotFoundException;
import com.board.game.mankala.repository.BoardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class GameEnded {

    private final BoardRepository boardRepository;

    /**
     *
     * @param boardState all board data as a board dto
     * @return board data if game ended would be all pits stones equal to zero, if not it does not changed
     */
    public Board checkGameEnded(Board boardState) {
        Board board = boardRepository.findById(boardState.getId()).orElseThrow(MankalaBoardNotFoundException::new);

        if (board.getRealPits().values().stream().allMatch(value -> value == 0) ||
                board.getBotPits().values().stream().allMatch(value -> value == 0)) {

            board.setRealStorage(board.getRealStorage() + board.getRealPits().values().stream().mapToInt(Integer::intValue).sum());
            board.getRealPits().replaceAll((k, v) -> v = 0);
            board.setBotStorage(board.getBotStorage() + board.getBotPits().values().stream().mapToInt(Integer::intValue).sum());
            board.getBotPits().replaceAll((k, v) -> v = 0);
            board.setGameState(GameState.STOPPED);
            whoIsWinner(board);

        }else {
            log.info("This mankala game has not ended yet!");
        }
        return boardRepository.save(board);
    }

    private void whoIsWinner(Board board) {
        if (board.getBotStorage() > board.getRealStorage()) {
            log.info("Bot won the game :)");
        }else if (board.getBotStorage() < board.getRealStorage()) {
            log.info("Real wonThe game :)");
        }else {
            log.info("No one won the game :|");
        }
    }
}
