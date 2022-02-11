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

    @Value("${kalaha.board.game.pits.value.max.limit}")
    private int pitsMaxValueLimit;

    @Value("${kalaha.board.game.pits.value.min.limit}")
    private int pitsMinValueLimit;

    @Value("${kalaha.board.game.pits.of.each.player}")
    private int eachPlayerPitsCount;

    @Value("${kalaha.board.game.pit.id.max.size}")
    private int pitsIdMaxSize;

    @Value("${kalaha.board.game.pit.id.min.size}")
    private int pitsIdMinSize;

    @Value("${kalaha.board.game.storage.min.value}")
    private int storageMinValue;

    @Value("${kalaha.board.game.all.pits.count}")
    private int allPitsCount;

    @Value("${kalaha.board.game.generate.bot.random.pit.id}")
    private final int botRandomPitId = RandomUtils.nextInt(pitsMinValueLimit, pitsMaxValueLimit);

    @Value("${kalaha.board.game.zero}")
    private int zero;
}
