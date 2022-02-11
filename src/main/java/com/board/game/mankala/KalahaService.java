package com.board.game.mankala;

import com.board.game.mankala.component.RealToBotPlayingStrategy;
import com.board.game.mankala.config.KalahaPropertiesConfiguration;
import com.board.game.mankala.data.Board;
import com.board.game.mankala.data.BoardDto;
import com.board.game.mankala.data.BoardRepository;
import com.board.game.mankala.exception.KalahaWebException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaService {

    private final KalahaPropertiesConfiguration kalahaSetting;
    private final BoardRepository boardRepository;
    private final RealToBotPlayingStrategy realToBotPlayingStrategy;

    public Board createBoard(){
        Map<Integer, Integer> botPlayer = new HashMap<>();
        Map<Integer, Integer> realPlayer = new HashMap<>();

        int counter = 0;

        while(counter <= kalahaSetting.getBotRandomPitId()) {

            if (counter < kalahaSetting.getEachPlayerPitsCount()) {
                botPlayer.put(counter, kalahaSetting.getPitsMaxLimit());
                counter++;
            }else {
                counter = kalahaSetting.getPitsMaxLimit();
                realPlayer.put(counter, kalahaSetting.getPitsMaxLimit());
                counter--;
            }
        }

       return boardRepository.save(Board.builder()
               .realStorage(0)
               .botStorage(0)
               .botPits(botPlayer)
               .realPits(realPlayer)
               .build());
    }

    public BoardDto makeTurn(BoardDto boardDto , int pitId){
        Board board = boardRepository.findById(boardDto.getId())
                .orElseThrow(() -> new KalahaWebException(String.format("This {%s} real player does not found!", boardDto)));

        realToBotPlayingStrategy.startPlayByRealPlayer(board, pitId);
        return BoardDto.builder().build();
    }

}
