package com.board.game.mankala.config;

import lombok.Getter;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@Getter
public class KalahaPropertiesConfiguration {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${kalaha.board.game.pits.max.limit}")
    private int pitsMaxLimit;

    @Value("${kalaha.board.game.pits.min.limit}")
    private int pitsMinLimit;

    @Value("${kalaha.board.game.pits.of.each.player}")
    private int eachPlayerPitsCount;

    @Value("${kalaha.board.game.all.pits.count}")
    private int allPitsCount;

    @Value("${kalaha.board.game.generate.bot.random.pit.id}")
    private int botRandomPitId = RandomUtils.nextInt(pitsMinLimit, pitsMaxLimit);

    @Value("${kalaha.board.game.zero}")
    private int zero;
}
