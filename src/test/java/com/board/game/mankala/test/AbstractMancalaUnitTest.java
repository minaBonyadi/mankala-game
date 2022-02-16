package com.board.game.mankala.test;

import com.board.game.mankala.component.RulesImpl;
import com.board.game.mankala.config.MankalaPropertiesConfiguration;
import com.board.game.mankala.repository.BoardRepository;
import com.board.game.mankala.service.MankalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
public class AbstractMancalaUnitTest {

    @Autowired
    public BoardRepository boardRepository;

    @Autowired
    private RulesImpl ruleHandler;

    @Autowired
    private MankalaPropertiesConfiguration mankalaConfig;

    @MockBean
    private MankalaService mankalaService;
}
