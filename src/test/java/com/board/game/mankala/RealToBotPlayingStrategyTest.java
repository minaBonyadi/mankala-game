package com.board.game.mankala;

import com.board.game.mankala.component.RealToBotPlayingStrategy;
import com.board.game.mankala.component.RulesManagementImpl;
import com.board.game.mankala.data.BoardRepository;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.mockito.Mockito.when;

public class RealToBotPlayingStrategyTest {

    private RealToBotPlayingStrategy realToBotPlaying;
    private BoardRepository boardRepository;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
//        RulesManagementImpl ruleHandler = new RulesManagementImpl();
//        realToBotPlaying = new RealToBotPlayingStrategy(ruleHandler);
    }

//    @Test
//    public void when_real_player_move_should_not_get_extra_pit_from_bot(){
//        //************************
//        //          Given
//        //************************
//        //Board state
//        RealToBotPlaying.realPlayer = new HashMap<>(){{
//            put(1, 1);
//            put(2, 0);
//            put(3, 0);
//            put(4, 0);
//            put(5, 4);
//            put(6, 0);
//        }};
//        RealToBotPlaying.realStorage = 21;
//
//        RealToBotPlaying.botPlayer = new HashMap<>(){{
//            put(1, 0);
//            put(2, 1);
//            put(3, 1);
//            put(4, 0);
//            put(5, 0);
//            put(6, 22);
//        }};
//        RealToBotPlaying.botStorage = 18;
//
//        //************************
//        //          WHEN
//        //************************
//        try (MockedStatic<RandomUtils> utilities = Mockito.mockStatic(RandomUtils.class)) {
//            when(commandHandler.getInput()).thenReturn(1);
//            utilities.when(() -> RandomUtils.nextInt(1,6)).thenReturn(3);
//            realToBotPlaying.handleTheGame();
//        }
//
//        //************************
//        //          THEN
//        //************************
//
//        assertThat(RealToBotPlaying.realStorage).isEqualTo(21);
//        assertThat(RealToBotPlaying.botStorage).isEqualTo(18); // +5
//
//        assertThat(RealToBotPlaying.realPlayer.get(1)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(2)).isEqualTo(1);
//        assertThat(RealToBotPlaying.realPlayer.get(3)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(4)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(5)).isEqualTo(4);
//        assertThat(RealToBotPlaying.realPlayer.get(6)).isZero();
//
//        assertThat(RealToBotPlaying.botPlayer.get(1)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(2)).isEqualTo(1);
//        assertThat(RealToBotPlaying.botPlayer.get(3)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(4)).isEqualTo(1);
//        assertThat(RealToBotPlaying.botPlayer.get(5)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(6)).isEqualTo(22);
//    }

//    @Test
//    public void when_bot_player_move_lead_to_get_extra_pit_from_real_1(){
//        //************************
//        //          Given
//        //************************
//        //Board state
//        RealToBotPlaying.realPlayer = new HashMap<>(){{
//            put(1, 4);
//            put(2, 4);
//            put(3, 4);
//            put(4, 1);
//            put(5, 3);
//            put(6, 4);
//        }};
//        RealToBotPlaying.realStorage = 20;
//
//        RealToBotPlaying.botPlayer = new HashMap<>(){{
//            put(1, 3);
//            put(2, 2);
//            put(3, 4);
//            put(4, 0);
//            put(5, 0);
//            put(6, 3);
//        }};
//        RealToBotPlaying.botStorage = 15;
//
//        //************************
//        //          WHEN
//        //************************
//        try (MockedStatic<RandomUtils> utilities = Mockito.mockStatic(RandomUtils.class)) {
//            when(commandHandler.getInput()).thenReturn(4);
//            utilities.when(() -> RandomUtils.nextInt(1,6)).thenReturn(2);
//            realToBotPlaying.handleTheGame();
//        }
//
//        //************************
//        //          THEN
//        //************************
//
//        assertThat(RealToBotPlaying.realStorage).isEqualTo(20);
//        assertThat(RealToBotPlaying.botStorage).isEqualTo(20); // +5
//
//        assertThat(RealToBotPlaying.realPlayer.get(1)).isEqualTo(4);
//        assertThat(RealToBotPlaying.realPlayer.get(2)).isEqualTo(4);
//        assertThat(RealToBotPlaying.realPlayer.get(3)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(4)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(5)).isEqualTo(4);
//        assertThat(RealToBotPlaying.realPlayer.get(6)).isEqualTo(4);
//
//        assertThat(RealToBotPlaying.botPlayer.get(1)).isEqualTo(3);
//        assertThat(RealToBotPlaying.botPlayer.get(2)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(3)).isEqualTo(5);
//        assertThat(RealToBotPlaying.botPlayer.get(4)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(5)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(6)).isEqualTo(3);
//    }
//
//    @Test
//    public void when_bot_player_move_lead_to_get_extra_pit_from_real_2(){
//        //************************
//        //          Given
//        //************************
//        //Board state
//        RealToBotPlaying.realPlayer = new HashMap<>(){{
//            put(1, 2);
//            put(2, 6);
//            put(3, 2);
//            put(4, 1);
//            put(5, 5);
//            put(6, 4);
//        }};
//        RealToBotPlaying.realStorage = 20;
//
//        RealToBotPlaying.botPlayer = new HashMap<>(){{
//            put(1, 3);
//            put(2, 4);
//            put(3, 1);
//            put(4, 0);
//            put(5, 0);
//            put(6, 4);
//        }};
//        RealToBotPlaying.botStorage = 15;
//
//        //************************
//        //          WHEN
//        //************************
//        try (MockedStatic<RandomUtils> utilities = Mockito.mockStatic(RandomUtils.class)) {
//            when(commandHandler.getInput()).thenReturn(4);
//            utilities.when(() -> RandomUtils.nextInt(1,6)).thenReturn(3);
//            realToBotPlaying.handleTheGame();
//        }
//
//        //************************
//        //          THEN
//        //************************
//
//        assertThat(RealToBotPlaying.realStorage).isEqualTo(20);
//        assertThat(RealToBotPlaying.botStorage).isEqualTo(18); // +3
//
//        assertThat(RealToBotPlaying.realPlayer.get(1)).isEqualTo(2);
//        assertThat(RealToBotPlaying.realPlayer.get(2)).isEqualTo(6);
//        assertThat(RealToBotPlaying.realPlayer.get(3)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(4)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(5)).isEqualTo(6);
//        assertThat(RealToBotPlaying.realPlayer.get(6)).isEqualTo(4);
//
//        assertThat(RealToBotPlaying.botPlayer.get(1)).isEqualTo(3);
//        assertThat(RealToBotPlaying.botPlayer.get(2)).isEqualTo(4);
//        assertThat(RealToBotPlaying.botPlayer.get(3)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(4)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(5)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(6)).isEqualTo(4);
//    }
//
//    @Test
//    public void when_real_player_move_lead_to_get_extra_pit_from_bot_1(){
//        //************************
//        //          Given
//        //************************
//        //Board state
//        RealToBotPlaying.realPlayer = new HashMap<>(){{
//            put(1, 3);
//            put(2, 2);
//            put(3, 1);
//            put(4, 0);
//            put(5, 3);
//            put(6, 3);
//        }};
//        RealToBotPlaying.realStorage=20;
//
//        RealToBotPlaying.botPlayer = new HashMap<>(){{
//            put(1, 4);
//            put(2, 4);
//            put(3, 4);
//            put(4, 2);
//            put(5, 4);
//            put(6, 4);
//        }};
//        RealToBotPlaying.botStorage=18;
//
//        //************************
//        //          WHEN
//        //************************
//        try (MockedStatic<RandomUtils> utilities = Mockito.mockStatic(RandomUtils.class)) {
//            when(commandHandler.getInput()).thenReturn(2);
//            utilities.when(() -> RandomUtils.nextInt(1,6)).thenReturn(4);
//            realToBotPlaying.handleTheGame();
//        }
//
//        //************************
//        //          THEN
//        //************************
//
//        assertThat(RealToBotPlaying.realStorage).isEqualTo(25); //+3
//        assertThat(RealToBotPlaying.botStorage).isEqualTo(18);
//
//        assertThat(RealToBotPlaying.realPlayer.get(1)).isEqualTo(3);
//        assertThat(RealToBotPlaying.realPlayer.get(2)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(3)).isEqualTo(2);
//        assertThat(RealToBotPlaying.realPlayer.get(4)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(5)).isEqualTo(3);
//        assertThat(RealToBotPlaying.realPlayer.get(6)).isEqualTo(3);
//
//        assertThat(RealToBotPlaying.botPlayer.get(1)).isEqualTo(4);
//        assertThat(RealToBotPlaying.botPlayer.get(2)).isEqualTo(4);
//        assertThat(RealToBotPlaying.botPlayer.get(3)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(4)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(5)).isEqualTo(5);
//        assertThat(RealToBotPlaying.botPlayer.get(6)).isEqualTo(5);
//    }
//
//    @Test
//    public void when_real_player_move_lead_to_get_extra_pit_from_bot_2(){
//        //************************
//        //          Given
//        //************************
//        //Board state
//        RealToBotPlaying.realPlayer = new HashMap<>(){{
//            put(1, 3);
//            put(2, 2);
//            put(3, 1);
//            put(4, 2);
//            put(5, 1);
//            put(6, 0);
//        }};
//        RealToBotPlaying.realStorage = 20;
//
//        RealToBotPlaying.botPlayer = new HashMap<>(){{
//            put(1, 3);
//            put(2, 5);
//            put(3, 4);
//            put(4, 2);
//            put(5, 4);
//            put(6, 4);
//        }};
//        RealToBotPlaying.botStorage = 18;
//
//        //************************
//        //          WHEN
//        //************************
//        try (MockedStatic<RandomUtils> utilities = Mockito.mockStatic(RandomUtils.class)) {
//            when(commandHandler.getInput()).thenReturn(4);
//            utilities.when(() -> RandomUtils.nextInt(1,6)).thenReturn(4);
//            realToBotPlaying.handleTheGame();
//        }
//
//        //************************
//        //          THEN
//        //************************
//
//        assertThat(RealToBotPlaying.realStorage).isEqualTo(24); //+4
//        assertThat(RealToBotPlaying.botStorage).isEqualTo(18);
//
//        assertThat(RealToBotPlaying.realPlayer.get(1)).isEqualTo(3);
//        assertThat(RealToBotPlaying.realPlayer.get(2)).isEqualTo(2);
//        assertThat(RealToBotPlaying.realPlayer.get(3)).isEqualTo(1);
//        assertThat(RealToBotPlaying.realPlayer.get(4)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(5)).isEqualTo(2);
//        assertThat(RealToBotPlaying.realPlayer.get(6)).isZero();
//
//        assertThat(RealToBotPlaying.botPlayer.get(1)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(2)).isEqualTo(5);
//        assertThat(RealToBotPlaying.botPlayer.get(3)).isEqualTo(4);
//        assertThat(RealToBotPlaying.botPlayer.get(4)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(5)).isEqualTo(5);
//        assertThat(RealToBotPlaying.botPlayer.get(6)).isEqualTo(5);
//    }
//
//    @Test
//    public void when_both_player_move_lead_to_switch_to_bot_and_real_player_boards(){
//        //************************
//        //          Given
//        //************************
//        //Board state
//        RealToBotPlaying.realPlayer = new HashMap<>(){{
//            put(1, 6);
//            put(2, 6);
//            put(3, 6);
//            put(4, 6);
//            put(5, 6);
//            put(6, 6);
//        }};
//        RealToBotPlaying.realStorage = 0;
//
//        RealToBotPlaying.botPlayer = new HashMap<>(){{
//            put(1, 6);
//            put(2, 6);
//            put(3, 6);
//            put(4, 6);
//            put(5, 6);
//            put(6, 6);
//        }};
//        RealToBotPlaying.botStorage = 0;
//        //************************
//        //          WHEN
//        //************************
//        try (MockedStatic<RandomUtils> utilities = Mockito.mockStatic(RandomUtils.class)) {
//            when(commandHandler.getInput()).thenReturn(4);
//            utilities.when(() -> RandomUtils.nextInt(1,6)).thenReturn(4);
//            realToBotPlaying.handleTheGame();
//        }
//        //************************
//        //          THEN
//        //************************
//
//        assertThat(RealToBotPlaying.botStorage).isEqualTo(1);
//        assertThat(RealToBotPlaying.realStorage).isEqualTo(1);
//
//        assertThat(RealToBotPlaying.realPlayer.get(1)).isEqualTo(7);
//        assertThat(RealToBotPlaying.realPlayer.get(2)).isEqualTo(7);
//        assertThat(RealToBotPlaying.realPlayer.get(3)).isEqualTo(7);
//        assertThat(RealToBotPlaying.realPlayer.get(4)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(5)).isEqualTo(7);
//        assertThat(RealToBotPlaying.realPlayer.get(6)).isEqualTo(7);
//
//        assertThat(RealToBotPlaying.botPlayer.get(1)).isEqualTo(7);
//        assertThat(RealToBotPlaying.botPlayer.get(2)).isEqualTo(7);
//        assertThat(RealToBotPlaying.botPlayer.get(3)).isEqualTo(7);
//        assertThat(RealToBotPlaying.botPlayer.get(4)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(5)).isEqualTo(7);
//        assertThat(RealToBotPlaying.botPlayer.get(6)).isEqualTo(7);
//    }
//
//    @Test
//    public void when_real_player_move_lead_to_circular_switch_to_bot_and_real_player_boards(){
//        //************************
//        //          Given
//        //************************
//        //Board state
//        RealToBotPlaying.realPlayer = new HashMap<>(){{
//            put(1, 1);
//            put(2, 0);
//            put(3, 2);
//            put(4, 10);
//            put(5, 12);
//            put(6, 0);
//        }};
//        RealToBotPlaying.realStorage = 11;
//
//        RealToBotPlaying.botPlayer = new HashMap<>(){{
//            put(1, 9);
//            put(2, 4);
//            put(3, 0);
//            put(4, 1);
//            put(5, 0);
//            put(6, 11);
//        }};
//        RealToBotPlaying.botStorage = 11;
//        //************************
//        //          WHEN
//        //************************
//        try (MockedStatic<RandomUtils> utilities = Mockito.mockStatic(RandomUtils.class)) {
//            when(commandHandler.getInput()).thenReturn(5);
//            utilities.when(() -> RandomUtils.nextInt(1,6)).thenReturn(5);
//            realToBotPlaying.handleTheGame();
//        }
//        //************************
//        //          THEN
//        //************************
//
//        assertThat(RealToBotPlaying.botStorage).isEqualTo(12);
//        assertThat(RealToBotPlaying.realStorage).isEqualTo(12);
//
//        assertThat(RealToBotPlaying.realPlayer.get(1)).isEqualTo(2);
//        assertThat(RealToBotPlaying.realPlayer.get(2)).isEqualTo(1);
//        assertThat(RealToBotPlaying.realPlayer.get(3)).isEqualTo(3);
//        assertThat(RealToBotPlaying.realPlayer.get(4)).isEqualTo(10);
//        assertThat(RealToBotPlaying.realPlayer.get(5)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(6)).isEqualTo(1);
//
//        assertThat(RealToBotPlaying.botPlayer.get(1)).isEqualTo(10);
//        assertThat(RealToBotPlaying.botPlayer.get(2)).isEqualTo(5);
//        assertThat(RealToBotPlaying.botPlayer.get(3)).isEqualTo(1);
//        assertThat(RealToBotPlaying.botPlayer.get(4)).isEqualTo(2);
//        assertThat(RealToBotPlaying.botPlayer.get(5)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(6)).isEqualTo(13);
//    }
//
//    @Test
//    public void when_bot_player_move_lead_to_circular_switch_to_real_and_bot_player_boards(){
//        //************************
//        //          Given
//        //************************
//        //Board state
//        RealToBotPlaying.realPlayer = new HashMap<>(){{
//            put(1, 1);
//            put(2, 1);
//            put(3, 1);
//            put(4, 10);
//            put(5, 12);
//            put(6, 0);
//        }};
//        RealToBotPlaying.realStorage = 11;
//
//        RealToBotPlaying.botPlayer = new HashMap<>(){{
//            put(1, 9);
//            put(2, 4);
//            put(3, 0);
//            put(4, 1);
//            put(5, 0);
//            put(6, 11);
//        }};
//        RealToBotPlaying.botStorage = 11;
//        //************************
//        //          WHEN
//        //************************
//        try (MockedStatic<RandomUtils> utilities = Mockito.mockStatic(RandomUtils.class)) {
//            when(commandHandler.getInput()).thenReturn(2);
//            utilities.when(() -> RandomUtils.nextInt(1,6)).thenReturn(6);
//            realToBotPlaying.handleTheGame();
//        }
//        //************************
//        //          THEN
//        //************************
//
//        assertThat(RealToBotPlaying.botStorage).isEqualTo(12);
//        assertThat(RealToBotPlaying.realStorage).isEqualTo(12);
//
//        assertThat(RealToBotPlaying.realPlayer.get(1)).isEqualTo(2);
//        assertThat(RealToBotPlaying.realPlayer.get(2)).isEqualTo(1);
//        assertThat(RealToBotPlaying.realPlayer.get(3)).isEqualTo(3);
//        assertThat(RealToBotPlaying.realPlayer.get(4)).isEqualTo(11);
//        assertThat(RealToBotPlaying.realPlayer.get(5)).isEqualTo(13);
//        assertThat(RealToBotPlaying.realPlayer.get(6)).isEqualTo(1);
//
//        assertThat(RealToBotPlaying.botPlayer.get(1)).isEqualTo(10);
//        assertThat(RealToBotPlaying.botPlayer.get(2)).isEqualTo(5);
//        assertThat(RealToBotPlaying.botPlayer.get(3)).isEqualTo(1);
//        assertThat(RealToBotPlaying.botPlayer.get(4)).isEqualTo(1);
//        assertThat(RealToBotPlaying.botPlayer.get(5)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(6)).isZero();
//    }
//
//    @Test
//    public void when_real_player_move_lead_to_end_the_game(){
//        //************************
//        //          Given
//        //************************
//        //Board state
//        RealToBotPlaying.realPlayer = new HashMap<>(){{
//            put(1, 0);
//            put(2, 0);
//            put(3, 0);
//            put(4, 0);
//            put(5, 0);
//            put(6, 1);
//        }};
//        RealToBotPlaying.realStorage = 34;
//
//        RealToBotPlaying.botPlayer = new HashMap<>(){{
//            put(1, 0);
//            put(2, 1);
//            put(3, 0);
//            put(4, 0);
//            put(5, 0);
//            put(6, 23);
//        }};
//        RealToBotPlaying.botStorage = 13;
//        //************************
//        //          WHEN
//        //************************
//        try (MockedStatic<RandomUtils> utilities = Mockito.mockStatic(RandomUtils.class)) {
//            when(commandHandler.getInput()).thenReturn(6);
//            utilities.when(() -> RandomUtils.nextInt(1,6)).thenReturn(2);
//            realToBotPlaying.handleTheGame();
//        }
//        //************************
//        //          THEN
//        //************************
//
//        assertThat(RealToBotPlaying.botStorage).isEqualTo(37);
//        assertThat(RealToBotPlaying.realStorage).isEqualTo(35);
//
//        assertThat(RealToBotPlaying.realPlayer.get(1)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(2)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(3)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(4)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(5)).isZero();
//        assertThat(RealToBotPlaying.realPlayer.get(6)).isZero();
//
//        assertThat(RealToBotPlaying.botPlayer.get(1)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(2)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(3)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(4)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(5)).isZero();
//        assertThat(RealToBotPlaying.botPlayer.get(6)).isZero();
//    }
}
