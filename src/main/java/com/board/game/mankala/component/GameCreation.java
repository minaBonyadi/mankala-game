package com.board.game.mankala.component;

import com.board.game.mankala.config.MankalaPropertiesConfiguration;
import com.board.game.mankala.entity.Board;
import com.board.game.mankala.enumeration.GameState;
import com.board.game.mankala.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class GameCreation {

    private final MankalaPropertiesConfiguration mankalaSetting;
    private final BoardRepository boardRepository;

    /**
     *
     * @return all a board created data as an entity with 6 stones for each 12 pits
     */
    public Board createBoard() {
        Map<Integer, Integer> botPlayer = new HashMap<>();
        Map<Integer, Integer> realPlayer = new HashMap<>();

        int counter = 0;
        int botPitId = mankalaSetting.getPitsIdMinSize();
        int realPitId = mankalaSetting.getPitsIdMinSize();

        while(counter < mankalaSetting.getAllPitsCount()) {

            if (counter < mankalaSetting.getEachPlayerPitsCount()) {
                botPlayer.put(botPitId++, mankalaSetting.getStonesMaxLimit());

            }else {
                realPlayer.put(realPitId++, mankalaSetting.getStonesMaxLimit());
            }
            counter++;
        }
        return boardRepository.save(Board.builder()
                .realStorage(mankalaSetting.getStorageMinValue())
                .botStorage(mankalaSetting.getStorageMinValue())
                .botPits(botPlayer)
                .realPits(realPlayer)
                .isBotTurn(true)
                .isRealTurn(true)
                .gameState(GameState.ACTIVE)
                .build());
    }
}
