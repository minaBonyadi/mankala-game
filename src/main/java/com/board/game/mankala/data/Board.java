package com.board.game.mankala.data;

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

    long id;
    Map<Integer, Integer> botPits;
    Map<Integer, Integer> realPits;
    int firstPlayerStorage;
    int botPlayerStorage;

}
