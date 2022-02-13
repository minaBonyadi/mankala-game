package com.board.game.mankala.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class BoardDto {

    String id;
    Map<Integer, Integer> realPits;
    Map<Integer, Integer> botPits;
    int botStorage;
    int realStorage;

}
