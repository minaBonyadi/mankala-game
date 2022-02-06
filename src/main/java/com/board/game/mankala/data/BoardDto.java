package com.board.game.mankala.data;

import lombok.Data;

import java.util.Map;

@Data
public class BoardDto {

    String id;
    Map<Integer, Integer> realPits;
    int realPlayerStorage;
}
