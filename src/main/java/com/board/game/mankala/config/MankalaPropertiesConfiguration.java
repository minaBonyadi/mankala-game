package com.board.game.mankala.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@Getter
public class MankalaPropertiesConfiguration {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${kalaha.board.game.stones.max.limit}")
    private int stonesMaxLimit;

    @Value("${kalaha.board.game.stones.min.limit}")
    private int stonesMinLimit;

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

    @Value("${kalaha.board.game.zero}")
    private int zero;
}
