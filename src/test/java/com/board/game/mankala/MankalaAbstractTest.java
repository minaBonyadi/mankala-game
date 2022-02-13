package com.board.game.mankala;

import com.board.game.mankala.config.TestRedisConfiguration;
import com.board.game.mankala.repository.BoardRepository;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = TestRedisConfiguration.class)
public abstract class MankalaAbstractTest {

    @Resource
    public BoardRepository boardRepository;

}
