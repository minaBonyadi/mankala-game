package com.board.game.mankala;

import com.board.game.mankala.component.RealToBotPlayingStrategy;
import com.board.game.mankala.data.Board;
import com.board.game.mankala.data.BoardDto;
import com.board.game.mankala.data.BoardRepository;
import com.board.game.mankala.exception.KalahaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KalahaService {

    @Value("${board.pits.max.limit}")
    private int PITS_MAX_LIMIT;
    @Value("${board.pits.of.each.player}")
    private int EACH_PLAYER_PITS_COUNT;
    @Value("${board.all.pits.count}")
    private int All_PITS_COUNT;

    private final BoardRepository boardRepository;
    private final RealToBotPlayingStrategy realToBotPlayingStrategy;

    public Board createBoard(){
        Map<Integer, Integer> botPlayer = new HashMap<>();
        Map<Integer, Integer> realPlayer = new HashMap<>();

        int counter = 0;

        while(counter <= All_PITS_COUNT) {

            if (counter < EACH_PLAYER_PITS_COUNT) {
                botPlayer.put(counter, PITS_MAX_LIMIT);
                counter++;
            }else {
                counter = PITS_MAX_LIMIT;
                realPlayer.put(counter, PITS_MAX_LIMIT);
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
                .orElseThrow(() -> new KalahaException(String.format("This {%s} real player does not found!", boardDto)));

        realToBotPlayingStrategy.startPlayByRealPlayer(board, pitId);
        return BoardDto.builder().build();
    }

}
