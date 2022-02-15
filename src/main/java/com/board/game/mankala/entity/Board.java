package com.board.game.mankala.entity;

import com.board.game.mankala.enumeration.GameState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@RedisHash("Board")
@AllArgsConstructor
public class Board implements Serializable {

    String id;
    Map<Integer, Integer> botPits;
    Map<Integer, Integer> realPits;
    int realStorage;
    int botStorage;
    boolean isRealTurn;
    boolean isBotTurn;
    GameState gameState;
}
