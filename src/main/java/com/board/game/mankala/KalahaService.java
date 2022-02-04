package com.board.game.mankala;

import com.board.game.mankala.model.Board;
import com.board.game.mankala.model.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KalahaService {

    //TODO make them by config properties
    private final int PITS_MAX_LIMIT = 6;
    private final int EACH_PLAYER_PITS_COUNT = 6;
    private final int All_PITS_COUNT = 12;

    private final BoardRepository boardRepository;

    public Board createBoard(){
        List<Integer> botPlayer = new ArrayList<>();
        List<Integer> realPlayer = new ArrayList<>();

        int counter = 1;

        while(counter <= All_PITS_COUNT) {

            if (counter <= EACH_PLAYER_PITS_COUNT) {
                botPlayer.add(PITS_MAX_LIMIT);
            }else {
                realPlayer.add(PITS_MAX_LIMIT);
            }
            counter++;
        }

       return boardRepository.save(Board.builder()
               .firstPlayerStorage(0)
               .botPlayerStorage(0)
               .botPits(botPlayer)
               .realPits(realPlayer)
               .build());
    }
}
