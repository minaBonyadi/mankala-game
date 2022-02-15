package com.board.game.mankala.dto.board;

import com.board.game.mankala.enumeration.GameState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BoardDto {

    String id;
    List<StoneDto> realPits;
    List<StoneDto> botPits;
    int botStorage;
    int realStorage;
    @JsonIgnore
    GameState gameState;

}
