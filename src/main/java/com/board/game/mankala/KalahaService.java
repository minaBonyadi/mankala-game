package com.board.game.mankala;

import com.board.game.mankala.component.RealToBotPlayingStrategy;
import com.board.game.mankala.config.KalahaPropertiesConfiguration;
import com.board.game.mankala.data.Board;
import com.board.game.mankala.data.BoardDto;
import com.board.game.mankala.data.BoardRepository;
import com.board.game.mankala.exception.KalahaBoardNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class KalahaService {

    private final KalahaPropertiesConfiguration kalahaSetting;
    private final BoardRepository boardRepository;
    private final RealToBotPlayingStrategy realToBotPlayingStrategy;

    public Board createBoard(){
        Map<Integer, Integer> botPlayer = new HashMap<>();
        Map<Integer, Integer> realPlayer = new HashMap<>();

        int counter = 0;
        int botPitId = kalahaSetting.getPitsIdMinSize();
        int realPitId = kalahaSetting.getPitsIdMinSize();

        while(counter < kalahaSetting.getAllPitsCount()) {

            if (counter < kalahaSetting.getEachPlayerPitsCount()) {
                botPlayer.put(botPitId++, kalahaSetting.getPitsMaxValueLimit());

            }else {
                realPlayer.put(realPitId++, kalahaSetting.getPitsMaxValueLimit());
            }
            counter++;
        }

       return boardRepository.save(Board.builder()
               .realStorage(kalahaSetting.getStorageMinValue())
               .botStorage(kalahaSetting.getStorageMinValue())
               .botPits(botPlayer)
               .realPits(realPlayer)
               .build());
    }

    public BoardDto makeTurnToRealPlayer(BoardDto boardDto , int pitId){
        Board board = boardRepository.findById(boardDto.getId())
                .orElseThrow(KalahaBoardNotFoundException::new);

        Board boardResult = realToBotPlayingStrategy.playByRealPlayer(board, pitId);

        return BoardDto.builder()
                .id(boardResult.getId())
                .realPits(boardResult.getRealPits())
                .botPits(boardResult.getBotPits())
                .realStorage(boardResult.getRealStorage())
                .botStorage(boardResult.getBotStorage())
                .build();
    }

    public BoardDto makeTurnToBotPlayer(BoardDto boardDto) {
        Board board = boardRepository.findById(boardDto.getId())
                .orElseThrow(KalahaBoardNotFoundException::new);

        Board boardResult = realToBotPlayingStrategy.playByBotPlayer(board);

        return BoardDto.builder()
                .id(boardResult.getId())
                .realPits(boardResult.getRealPits())
                .botPits(boardResult.getBotPits())
                .realStorage(boardResult.getRealStorage())
                .botStorage(boardResult.getBotStorage())
                .build();
    }

    public BoardDto checkGameEnded(BoardDto boardDto) {
        Board board = boardRepository.findById(boardDto.getId())
                .orElseThrow(KalahaBoardNotFoundException::new);

        Board boardResult = realToBotPlayingStrategy.checkGameEnded(board);

        return BoardDto.builder()
                .id(boardResult.getId())
                .realPits(boardResult.getRealPits())
                .botPits(boardResult.getBotPits())
                .realStorage(boardResult.getRealStorage())
                .botStorage(boardResult.getBotStorage())
                .build();
    }
}
