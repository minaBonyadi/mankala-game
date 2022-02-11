package com.board.game.mankala;

import com.board.game.mankala.config.TestRedisConfiguration;
import com.board.game.mankala.data.Board;
import com.board.game.mankala.data.BoardRepository;
import com.board.game.mankala.exception.KalahaBoardNotFoundException;
import com.board.game.mankala.exception.KalahaWebException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@AutoConfigureMockMvc
class KalahaServiceTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private BoardRepository boardRepository;

    private static final String CREATE_BOARD_ENDPOINT = "/game/create-board";
    private static final String REAL_PLAYER_MAKE_TURN_ENDPOINT = "/game/real/make-turn";
    private static final String BOT_PLAYER_MAKE_TURN_ENDPOINT = "/game/bot/make-turn";
//    private static final String CHECK_GAME_ENDED = "/game/check-game-ended";

    @Test
    void test_board_creation_service() throws Exception {
        //************************
        //          Given
        //************************
        //************************
        //          WHEN
        //************************
        MvcResult responseBody = mockMvc.perform(MockMvcRequestBuilders.get(CREATE_BOARD_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Board boardResult = boardRepository.findAll().get(0);
        //************************
        //          THEN
        //************************
        // check rest response
        assertThat(responseBody.getResponse().getContentAsString()).isNotEmpty();
        assertThat(boardResult.getRealStorage()).isZero();
        assertThat(boardResult.getBotStorage()).isZero();

        assertThat(boardResult.getRealPits()).containsEntry(1, 6);
        assertThat(boardResult.getRealPits()).containsEntry(2, 6);
        assertThat(boardResult.getRealPits()).containsEntry(3, 6);
        assertThat(boardResult.getRealPits()).containsEntry(4, 6);
        assertThat(boardResult.getRealPits()).containsEntry(5, 6);
        assertThat(boardResult.getRealPits()).containsEntry(6, 6);

        assertThat(boardResult.getBotPits()).containsEntry(1, 6);
        assertThat(boardResult.getBotPits()).containsEntry(2, 6);
        assertThat(boardResult.getBotPits()).containsEntry(3, 6);
        assertThat(boardResult.getBotPits()).containsEntry(4, 6);
        assertThat(boardResult.getBotPits()).containsEntry(5, 6);
        assertThat(boardResult.getBotPits()).containsEntry(6, 6);
    }

    @Test
    void test_real_player_make_turn_at_first_turn_service() throws Exception {
        //************************
        //          Given
        //************************
        //Board state
        HashMap<Integer, Integer> realPits = new HashMap<>() {{
            put(1, 6);
            put(2, 6);
            put(3, 6);
            put(4, 6);
            put(5, 6);
            put(6, 6);
        }};

        HashMap<Integer, Integer> botPits = new HashMap<>() {{
            put(1, 6);
            put(2, 6);
            put(3, 6);
            put(4, 6);
            put(5, 6);
            put(6, 6);
        }};

        Board board = Board.builder()
                .id("50b66cc4-d64a-456b-b202-2c258100f057")
                .realStorage(0)
                .botStorage(0)
                .realPits(realPits)
                .botPits(botPits).build();

        boardRepository.save(board);

        String str = "{\"id\":\"50b66cc4-d64a-456b-b202-2c258100f057\",\"botPits\":{\"1\":6,\"2\":6,\"3\":6,\"4\":6,\"5\":6,\"6\":6}," +
                "\"realPits\":{\"1\":6,\"2\":6,\"3\":6,\"4\":6,\"5\":6,\"6\":6},\"realStorage\":0,\"botStorage\":0}";

        //************************
        //          WHEN
        //************************
        MvcResult responseBody = mockMvc.perform(MockMvcRequestBuilders.post(REAL_PLAYER_MAKE_TURN_ENDPOINT+"/6")
                .content(str)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String restResponse = responseBody.getResponse().getContentAsString();
        //************************
        //          THEN
        //************************
        // check rest response
        assertThat(responseBody.getResponse().getContentAsString()).isNotEmpty();

        JSONAssert.assertEquals(restResponse, "{\"id\":\"50b66cc4-d64a-456b-b202-2c258100f057\",\"realPits\":{\"1\":6,\"2\":6,\"3\":6,\"4\":6,\"5\":6,\"6\":0}," +
                "\"botPits\":{\"1\":6,\"2\":7,\"3\":7,\"4\":7,\"5\":7,\"6\":7},\"botStorage\":0,\"realStorage\":1}", true);
    }

    @Test
    void test_real_player_make_turn_at_middle_game_turn_service() throws Exception {
        //************************
        //          Given
        //************************
        //Board state
        HashMap<Integer, Integer> realPits = new HashMap<>() {{
            put(1, 1);
            put(2, 0);
            put(3, 0);
            put(4, 0);
            put(5, 4);
            put(6, 0);
        }};

        HashMap<Integer, Integer> botPits = new HashMap<>() {{
            put(1, 0);
            put(2, 1);
            put(3, 1);
            put(4, 0);
            put(5, 0);
            put(6, 22);
        }};

        Board board = Board.builder()
                .id("50b66cc4-d64a-456b-b202-2c258100f057")
                .realStorage(18)
                .botStorage(20)
                .realPits(realPits)
                .botPits(botPits).build();

        boardRepository.save(board);

        String str = "{\"id\":\"50b66cc4-d64a-456b-b202-2c258100f057\",\"botPits\":{\"1\":0,\"2\":1,\"3\":1,\"4\":0,\"5\":0,\"6\":22}," +
                "\"realPits\":{\"1\":1,\"2\":0,\"3\":0,\"4\":0,\"5\":4,\"6\":0},\"realStorage\":18,\"botStorage\":20}";

        //************************
        //          WHEN
        //************************
        MvcResult responseBody = mockMvc.perform(MockMvcRequestBuilders.post(REAL_PLAYER_MAKE_TURN_ENDPOINT+"/1")
                .content(str)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String restResponse = responseBody.getResponse().getContentAsString();
        //************************
        //          THEN
        //************************
        // check rest response
        assertThat(responseBody.getResponse().getContentAsString()).isNotEmpty();

        JSONAssert.assertEquals(restResponse, "{\"id\":\"50b66cc4-d64a-456b-b202-2c258100f057\",\"realPits\":{\"1\":0,\"2\":0,\"3\":0,\"4\":0,\"5\":4,\"6\":0}" +
                ",\"botPits\":{\"1\":0,\"2\":0,\"3\":1,\"4\":0,\"5\":0,\"6\":22},\"botStorage\":20,\"realStorage\":20}", true);
    }

    @Test
    void test_real_player_make_turn_and_choose_zero_pit_value_service() throws Exception {
        //************************
        //          Given
        //************************
        //Board state
        HashMap<Integer, Integer> realPits = new HashMap<>() {{
            put(1, 1);
            put(2, 0);
            put(3, 0);
            put(4, 0);
            put(5, 4);
            put(6, 0);
        }};

        HashMap<Integer, Integer> botPits = new HashMap<>() {{
            put(1, 0);
            put(2, 1);
            put(3, 1);
            put(4, 0);
            put(5, 0);
            put(6, 22);
        }};

        Board board = Board.builder()
                .id("50b66cc4-d64a-456b-b202-2c258100f057")
                .realStorage(18)
                .botStorage(20)
                .realPits(realPits)
                .botPits(botPits).build();

        boardRepository.save(board);

        String str = "{\"id\":\"50b66cc4-d64a-456b-b202-2c258100f057\",\"botPits\":{\"1\":0,\"2\":1,\"3\":1,\"4\":0,\"5\":0,\"6\":22}," +
                "\"realPits\":{\"1\":1,\"2\":0,\"3\":0,\"4\":0,\"5\":4,\"6\":0},\"realStorage\":18,\"botStorage\":20}";

        //************************
        //          WHEN
        //************************
        //************************
        //          THEN
        //************************
        mockMvc.perform(MockMvcRequestBuilders.post(REAL_PLAYER_MAKE_TURN_ENDPOINT+"/2")
                .content(str)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof KalahaWebException))
                .andExpect(result -> assertEquals("Chosen pit does not filled, please choose another pit!",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andReturn();
    }
}
