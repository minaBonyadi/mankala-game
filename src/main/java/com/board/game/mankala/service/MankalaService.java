package com.board.game.mankala.service;

import com.board.game.mankala.component.GameCreation;
import com.board.game.mankala.component.MakeTurn;
import com.board.game.mankala.config.MankalaPropertiesConfiguration;
import com.board.game.mankala.dto.board.BoardDto;
import com.board.game.mankala.entity.Board;
import com.board.game.mankala.exception.MankalaBoardNotFoundException;
import com.board.game.mankala.repository.BoardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MankalaService {

    private final MankalaPropertiesConfiguration mankalaConfig;
    private final MakeTurn makeTurnManagement;
    private final GameCreation gameManagement;
    private final BoardRepository boardRepository;

    /**
     *
     * @return Create a game with 6 stones in each pits
     */
    public BoardDto createGame() {
        Board board = gameManagement.createBoard();
        log.info(String.format("A new game is create with id {%s}", board.getId()));

        return BoardDto.builder()
                .id(board.getId())
                .realStorage(mankalaConfig.getStorageMinValue())
                .botStorage(mankalaConfig.getStorageMinValue())
                .botPits(board.getBotPits())
                .realPits(board.getBotPits())
                .build();
    }

    /**
     *
     * @param boardDto is a data transfer object with all a board data
     * @param pitId is a selected pit id from real player
     * @return a board dto which had effect of moving both real and bot player
     */
    public BoardDto makeTurn(BoardDto boardDto , int pitId) {
        makeTurnManagement.makeTurn(boardDto, pitId);
        Board boardResult = boardRepository.findById(boardDto.getId()).orElseThrow(MankalaBoardNotFoundException::new);

        return BoardDto.builder()
                .id(boardResult.getId())
                .realPits(boardResult.getRealPits())
                .botPits(boardResult.getBotPits())
                .realStorage(boardResult.getRealStorage())
                .botStorage(boardResult.getBotStorage())
                .build();
    }

}
