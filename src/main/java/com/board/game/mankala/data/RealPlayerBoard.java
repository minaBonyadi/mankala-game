package com.board.game.mankala.data;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class RealPlayerBoard {

    Map<Integer, Integer> realPits;
    int realStorage;

}
