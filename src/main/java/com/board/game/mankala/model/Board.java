package com.board.game.mankala.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@RedisHash("Board")
@AllArgsConstructor
public class Board implements Serializable {

    String id;
    List<Integer> botPits;
    List<Integer> realPits;
    int firstPlayerStorage;
    int botPlayerStorage;

}
