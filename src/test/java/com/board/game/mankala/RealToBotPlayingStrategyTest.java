package com.board.game.mankala;

import com.board.game.mankala.component.RealToBotPlayingStrategy;
import com.board.game.mankala.component.RealToBotStrategyRulesImpl;
import com.board.game.mankala.config.KalahaPropertiesConfiguration;
import com.board.game.mankala.data.Board;
import com.board.game.mankala.data.BoardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
class RealToBotPlayingStrategyTest {

    @Autowired
    private RealToBotPlayingStrategy realToBotPlaying;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private RealToBotStrategyRulesImpl ruleHandler;
    
    @MockBean
    private KalahaPropertiesConfiguration kalahaSetting;
    @MockBean
    private KalahaService kalahaService;

    @Test
    void when_real_player_move_should_not_get_extra_pit_from_bot() {
        //************************
        //          Given
        //************************
        //Board state
        HashMap<Integer, Integer> realPits = new HashMap<>(){{
            put(1, 1);
            put(2, 0);
            put(3, 0);
            put(4, 0);
            put(5, 4);
            put(6, 0);
        }};

        HashMap<Integer, Integer> botPits = new HashMap<>(){{
            put(1, 0);
            put(2, 1);
            put(3, 1);
            put(4, 0);
            put(5, 0);
            put(6, 22);
        }};

        Board board  = Board.builder()
                .id("123")
                .realStorage(21)
                .botStorage(18)
                .realPits(realPits)
                .botPits(botPits).build();

        boardRepository.save(board);
        //************************
        //          WHEN
        //************************
        when(kalahaSetting.getBotRandomPitId()).thenReturn(3);
        Board boardResult = realToBotPlaying.startPlayByRealPlayer(board, 1);

        //************************
        //          THEN
        //************************

        assertThat(boardResult.getRealStorage()).isEqualTo(23);
        assertThat(boardResult.getBotStorage()).isEqualTo(18); // +5

        assertThat(boardResult.getRealPits().get(1)).isZero();
        assertThat(boardResult.getRealPits().get(2)).isZero();
        assertThat(boardResult.getRealPits().get(3)).isZero();
        assertThat(boardResult.getRealPits().get(4)).isZero();
        assertThat(boardResult.getRealPits().get(5)).isEqualTo(4);
        assertThat(boardResult.getRealPits().get(6)).isZero();

        assertThat(boardResult.getBotPits().get(1)).isZero();
        assertThat(boardResult.getBotPits().get(2)).isEqualTo(1);
        assertThat(boardResult.getBotPits().get(3)).isZero();
        assertThat(boardResult.getBotPits().get(4)).isZero();
        assertThat(boardResult.getBotPits().get(5)).isZero();
        assertThat(boardResult.getBotPits().get(6)).isEqualTo(22);
    }

    @Test
    void when_bot_player_move_lead_to_get_extra_pit_from_real_1(){
        //************************
        //          Given
        //************************
        //Board state
        HashMap<Integer, Integer> realPits = new HashMap<>(){{
            put(1, 4);
            put(2, 4);
            put(3, 4);
            put(4, 1);
            put(5, 3);
            put(6, 4);
        }};

        HashMap<Integer, Integer> botPits = new HashMap<>(){{
            put(1, 3);
            put(2, 2);
            put(3, 4);
            put(4, 0);
            put(5, 0);
            put(6, 3);
        }};

        Board board  = Board.builder()
                .id("123")
                .realStorage(20)
                .botStorage(15)
                .realPits(realPits)
                .botPits(botPits).build();

        boardRepository.save(board);

        //************************
        //          WHEN
        //************************
        when(kalahaSetting.getBotRandomPitId()).thenReturn(2); //bot choose
        Board boardResult = realToBotPlaying.startPlayByRealPlayer(board, 4); //real choose
        //************************
        //          THEN
        //************************

        assertThat(boardResult.getRealStorage()).isEqualTo(20);
        assertThat(boardResult.getBotStorage()).isEqualTo(16);

        assertThat(boardResult.getRealPits().get(1)).isEqualTo(4);
        assertThat(boardResult.getRealPits().get(2)).isEqualTo(4);
        assertThat(boardResult.getRealPits().get(3)).isEqualTo(4);
        assertThat(boardResult.getRealPits().get(4)).isZero();
        assertThat(boardResult.getRealPits().get(5)).isEqualTo(4);
        assertThat(boardResult.getRealPits().get(6)).isEqualTo(4);

        assertThat(boardResult.getBotPits().get(1)).isEqualTo(4);
        assertThat(boardResult.getBotPits().get(2)).isZero();
        assertThat(boardResult.getBotPits().get(3)).isEqualTo(4);
        assertThat(boardResult.getBotPits().get(4)).isZero();
        assertThat(boardResult.getBotPits().get(5)).isZero();
        assertThat(boardResult.getBotPits().get(6)).isEqualTo(3);
    }

    @Test
    void when_bot_player_move_lead_to_get_extra_pit_from_real_2(){
        //************************
        //          Given
        //************************
        //Board state
        HashMap<Integer, Integer> realPits = new HashMap<>(){{
            put(1, 2);
            put(2, 6);
            put(3, 2);
            put(4, 5);
            put(5, 1);
            put(6, 4);
        }};

        HashMap<Integer, Integer> botPits = new HashMap<>(){{
            put(1, 3);
            put(2, 0);
            put(3, 1);
            put(4, 0);
            put(5, 0);
            put(6, 4);
        }};

        Board board  = Board.builder()
                .id("123")
                .realStorage(20)
                .botStorage(15)
                .realPits(realPits)
                .botPits(botPits).build();

        boardRepository.save(board);

        //************************
        //          WHEN
        //************************
        when(kalahaSetting.getBotRandomPitId()).thenReturn(3); //bot choose
        Board boardResult = realToBotPlaying.startPlayByRealPlayer(board, 3); //real choose

        //************************
        //          THEN
        //************************

        assertThat(boardResult.getRealStorage()).isEqualTo(20);
        assertThat(boardResult.getBotStorage()).isEqualTo(22); // +3

        assertThat(boardResult.getRealPits().get(1)).isEqualTo(2);
        assertThat(boardResult.getRealPits().get(2)).isZero();
        assertThat(boardResult.getRealPits().get(3)).isZero();
        assertThat(boardResult.getRealPits().get(4)).isEqualTo(6);
        assertThat(boardResult.getRealPits().get(5)).isEqualTo(2);
        assertThat(boardResult.getRealPits().get(6)).isEqualTo(4);

        assertThat(boardResult.getBotPits().get(1)).isEqualTo(3);
        assertThat(boardResult.getBotPits().get(2)).isZero();
        assertThat(boardResult.getBotPits().get(3)).isZero();
        assertThat(boardResult.getBotPits().get(4)).isZero();
        assertThat(boardResult.getBotPits().get(5)).isZero();
        assertThat(boardResult.getBotPits().get(6)).isEqualTo(4);
    }

    @Test
    void when_real_player_move_lead_to_get_extra_pit_from_bot_1(){
        //************************
        //          Given
        //************************
        //Board state
        HashMap<Integer, Integer> realPits = new HashMap<>(){{
            put(1, 3);
            put(2, 2);
            put(3, 1);
            put(4, 0);
            put(5, 3);
            put(6, 3);
        }};

        HashMap<Integer, Integer> botPits =  new HashMap<>(){{
            put(1, 4);
            put(2, 4);
            put(3, 4);
            put(4, 2);
            put(5, 4);
            put(6, 4);
        }};

        Board board  = Board.builder()
                .id("123")
                .realStorage(20)
                .botStorage(18)
                .realPits(realPits)
                .botPits(botPits).build();

        boardRepository.save(board);

        //************************
        //          WHEN
        //************************
        when(kalahaSetting.getBotRandomPitId()).thenReturn(3); //bot choose
        Board boardResult = realToBotPlaying.startPlayByRealPlayer(board, 2); //real choose

        //************************
        //          THEN
        //************************

        assertThat(boardResult.getRealStorage()).isEqualTo(23); //+3
        assertThat(boardResult.getBotStorage()).isEqualTo(19);

        assertThat(boardResult.getRealPits().get(1)).isEqualTo(4);
        assertThat(boardResult.getRealPits().get(2)).isZero();
        assertThat(boardResult.getRealPits().get(3)).isEqualTo(2);
        assertThat(boardResult.getRealPits().get(4)).isZero();
        assertThat(boardResult.getRealPits().get(5)).isEqualTo(3);
        assertThat(boardResult.getRealPits().get(6)).isEqualTo(3);

        assertThat(boardResult.getBotPits().get(1)).isEqualTo(5);
        assertThat(boardResult.getBotPits().get(2)).isEqualTo(5);
        assertThat(boardResult.getBotPits().get(3)).isZero();
        assertThat(boardResult.getBotPits().get(4)).isZero();
        assertThat(boardResult.getBotPits().get(5)).isEqualTo(4);
        assertThat(boardResult.getBotPits().get(6)).isEqualTo(4);
    }

    @Test
    void when_real_player_move_lead_to_get_extra_pit_from_bot_2(){
        //************************
        //          Given
        //************************
        //Board state
        HashMap<Integer, Integer> realPits = new HashMap<>(){{
            put(1, 3);
            put(2, 2);
            put(3, 1);
            put(4, 2);
            put(5, 1);
            put(6, 0);
        }};

        HashMap<Integer, Integer> botPits = new HashMap<>(){{
            put(1, 4);
            put(2, 4);
            put(3, 2);
            put(4, 4);
            put(5, 5);
            put(6, 3);
        }};

        Board board  = Board.builder()
                .id("123")
                .realStorage(20)
                .botStorage(18)
                .realPits(realPits)
                .botPits(botPits).build();

        boardRepository.save(board);

        //************************
        //          WHEN
        //************************
        when(kalahaSetting.getBotRandomPitId()).thenReturn(4); //bot choose
        Board boardResult = realToBotPlaying.startPlayByRealPlayer(board, 4); //real choose

        //************************
        //          THEN
        //************************

        assertThat(boardResult.getRealStorage()).isEqualTo(24); //+4
        assertThat(boardResult.getBotStorage()).isEqualTo(19);

        assertThat(boardResult.getRealPits().get(1)).isEqualTo(3);
        assertThat(boardResult.getRealPits().get(2)).isEqualTo(2);
        assertThat(boardResult.getRealPits().get(3)).isEqualTo(1);
        assertThat(boardResult.getRealPits().get(4)).isZero();
        assertThat(boardResult.getRealPits().get(5)).isEqualTo(2);
        assertThat(boardResult.getRealPits().get(6)).isZero();

        assertThat(boardResult.getBotPits().get(1)).isEqualTo(5);
        assertThat(boardResult.getBotPits().get(2)).isEqualTo(5);
        assertThat(boardResult.getBotPits().get(3)).isEqualTo(3);
        assertThat(boardResult.getBotPits().get(4)).isZero();
        assertThat(boardResult.getBotPits().get(5)).isEqualTo(5);
        assertThat(boardResult.getBotPits().get(6)).isZero();
    }

    @Test
    void when_both_player_move_lead_to_switch_to_bot_and_real_player_boards(){
        //************************
        //          Given
        //************************
        //Board state
        HashMap<Integer, Integer> realPits = new HashMap<>(){{
            put(1, 6);
            put(2, 6);
            put(3, 6);
            put(4, 6);
            put(5, 6);
            put(6, 6);
        }};

        HashMap<Integer, Integer> botPits =new HashMap<>(){{
            put(1, 6);
            put(2, 6);
            put(3, 6);
            put(4, 6);
            put(5, 6);
            put(6, 6);
        }};

        Board board  = Board.builder()
                .id("123")
                .realStorage(0)
                .botStorage(0)
                .realPits(realPits)
                .botPits(botPits).build();

        boardRepository.save(board);

        //************************
        //          WHEN
        //************************
        when(kalahaSetting.getBotRandomPitId()).thenReturn(1); //bot choose
        Board boardResult = realToBotPlaying.startPlayByRealPlayer(board, 1); //real choose
        //************************
        //          THEN
        //************************

        assertThat(boardResult.getBotStorage()).isEqualTo(1);
        assertThat(boardResult.getRealStorage()).isEqualTo(1);

        assertThat(boardResult.getRealPits().get(1)).isEqualTo(1);
        assertThat(boardResult.getRealPits().get(2)).isEqualTo(8);
        assertThat(boardResult.getRealPits().get(3)).isEqualTo(8);
        assertThat(boardResult.getRealPits().get(4)).isEqualTo(8);
        assertThat(boardResult.getRealPits().get(5)).isEqualTo(8);
        assertThat(boardResult.getRealPits().get(6)).isEqualTo(7);

        assertThat(boardResult.getBotPits().get(1)).isEqualTo(0);
        assertThat(boardResult.getBotPits().get(2)).isEqualTo(6);
        assertThat(boardResult.getBotPits().get(3)).isEqualTo(6);
        assertThat(boardResult.getBotPits().get(4)).isEqualTo(6);
        assertThat(boardResult.getBotPits().get(5)).isEqualTo(6);
        assertThat(boardResult.getBotPits().get(6)).isEqualTo(6);
    }

    @Test
    void when_real_player_move_lead_to_circular_switch_to_bot_and_real_player_boards(){
        //************************
        //          Given
        //************************
        //Board state
        HashMap<Integer, Integer> realPits = new HashMap<>(){{
            put(1, 1);
            put(2, 0);
            put(3, 2);
            put(4, 10);
            put(5, 12);
            put(6, 0);
        }};

        HashMap<Integer, Integer> botPits = new HashMap<>(){{
            put(1, 11);
            put(2, 0);
            put(3, 1);
            put(4, 0);
            put(5, 4);
            put(6, 9);
        }};

        Board board  = Board.builder()
                .id("123")
                .realStorage(11)
                .botStorage(11)
                .realPits(realPits)
                .botPits(botPits).build();

        boardRepository.save(board);

        //************************
        //          WHEN
        //************************
        when(kalahaSetting.getBotRandomPitId()).thenReturn(5); //bot choose
        Board boardResult = realToBotPlaying.startPlayByRealPlayer(board, 5); //real choose

        //************************
        //          THEN
        //************************

        assertThat(boardResult.getBotStorage()).isEqualTo(13);
        assertThat(boardResult.getRealStorage()).isEqualTo(12);

        assertThat(boardResult.getRealPits().get(1)).isEqualTo(2);
        assertThat(boardResult.getRealPits().get(2)).isEqualTo(1);
        assertThat(boardResult.getRealPits().get(3)).isEqualTo(3);
        assertThat(boardResult.getRealPits().get(4)).isEqualTo(10);
        assertThat(boardResult.getRealPits().get(5)).isZero();
        assertThat(boardResult.getRealPits().get(6)).isEqualTo(1);

        assertThat(boardResult.getBotPits().get(1)).isEqualTo(13);
        assertThat(boardResult.getBotPits().get(2)).isEqualTo(2);
        assertThat(boardResult.getBotPits().get(3)).isEqualTo(3);
        assertThat(boardResult.getBotPits().get(4)).isEqualTo(2);
        assertThat(boardResult.getBotPits().get(5)).isZero();
        assertThat(boardResult.getBotPits().get(6)).isEqualTo(10);
    }

    @Test
    void when_bot_player_move_lead_to_circular_switch_to_real_and_bot_player_boards(){
        //************************
        //          Given
        //************************
        //Board state
        HashMap<Integer, Integer> realPits = new HashMap<>(){{
            put(1, 1);
            put(2, 1);
            put(3, 1);
            put(4, 10);
            put(5, 12);
            put(6, 0);
        }};

        HashMap<Integer, Integer> botPits = new HashMap<>(){{
            put(1, 11);
            put(2, 0);
            put(3, 1);
            put(4, 0);
            put(5, 4);
            put(6, 9);
        }};

        Board board  = Board.builder()
                .id("123")
                .realStorage(11)
                .botStorage(11)
                .realPits(realPits)
                .botPits(botPits).build();

        boardRepository.save(board);

        //************************
        //          WHEN
        //************************
        when(kalahaSetting.getBotRandomPitId()).thenReturn(6); //bot choose
        Board boardResult = realToBotPlaying.startPlayByRealPlayer(board, 2); //real choose
        //************************
        //          THEN
        //************************

        assertThat(boardResult.getBotStorage()).isEqualTo(12);
        assertThat(boardResult.getRealStorage()).isEqualTo(11);

        assertThat(boardResult.getRealPits().get(1)).isEqualTo(2);
        assertThat(boardResult.getRealPits().get(2)).isEqualTo(1);
        assertThat(boardResult.getRealPits().get(3)).isEqualTo(3);
        assertThat(boardResult.getRealPits().get(4)).isEqualTo(10);
        assertThat(boardResult.getRealPits().get(5)).isEqualTo(12);
        assertThat(boardResult.getRealPits().get(6)).isZero();

        assertThat(boardResult.getBotPits().get(1)).isEqualTo(12);
        assertThat(boardResult.getBotPits().get(2)).isEqualTo(1);
        assertThat(boardResult.getBotPits().get(3)).isEqualTo(2);
        assertThat(boardResult.getBotPits().get(4)).isEqualTo(1);
        assertThat(boardResult.getBotPits().get(5)).isEqualTo(5);
        assertThat(boardResult.getBotPits().get(6)).isZero();
    }

    @Test
    void when_real_player_move_lead_to_end_the_game(){
        //************************
        //          Given
        //************************
        //Board state
        HashMap<Integer, Integer> realPits = new HashMap<>(){{
            put(1, 0);
            put(2, 0);
            put(3, 0);
            put(4, 0);
            put(5, 0);
            put(6, 1);
        }};

        HashMap<Integer, Integer> botPits = new HashMap<>(){{
            put(1, 23);
            put(2, 0);
            put(3, 0);
            put(4, 0);
            put(5, 1);
            put(6, 0);
        }};

        Board board  = Board.builder()
                .id("123")
                .realStorage(34)
                .botStorage(13)
                .realPits(realPits)
                .botPits(botPits).build();

        boardRepository.save(board);

        //************************
        //          WHEN
        //************************
        when(kalahaSetting.getBotRandomPitId()).thenReturn(2); //bot choose
        Board boardResult = realToBotPlaying.startPlayByRealPlayer(board, 6); //real choose

        //************************
        //          THEN
        //************************

        assertThat(boardResult.getBotStorage()).isEqualTo(37);
        assertThat(boardResult.getRealStorage()).isEqualTo(35);

        assertThat(boardResult.getRealPits().get(1)).isZero();
        assertThat(boardResult.getRealPits().get(2)).isZero();
        assertThat(boardResult.getRealPits().get(3)).isZero();
        assertThat(boardResult.getRealPits().get(4)).isZero();
        assertThat(boardResult.getRealPits().get(5)).isZero();
        assertThat(boardResult.getRealPits().get(6)).isZero();

        assertThat(boardResult.getBotPits().get(1)).isZero();
        assertThat(boardResult.getBotPits().get(2)).isZero();
        assertThat(boardResult.getBotPits().get(3)).isZero();
        assertThat(boardResult.getBotPits().get(4)).isZero();
        assertThat(boardResult.getBotPits().get(5)).isZero();
        assertThat(boardResult.getBotPits().get(6)).isZero();
    }
}
