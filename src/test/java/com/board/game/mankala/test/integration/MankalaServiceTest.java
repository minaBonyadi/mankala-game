package com.board.game.mankala.test.integration;

import com.board.game.mankala.config.TestRedisConfiguration;
import com.board.game.mankala.entity.Board;
import com.board.game.mankala.enumeration.GameState;
import com.board.game.mankala.exception.KalahaOutOfBandException;
import com.board.game.mankala.exception.KalahaWebException;
import com.board.game.mankala.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import redis.embedded.RedisServer;

import java.util.HashMap;
import java.util.Objects;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class MankalaServiceTest {

    private static final String CREATE_BOARD_ENDPOINT = "/games/create";
    private static final String REAL_TO_BOT_PLAYER_MAKE_TURN_ENDPOINT = "/games/make-turn";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    public BoardRepository boardRepository;

    @BeforeEach
    public void setUp() {
        try {
            RedisServer redisServer = RedisServer.builder().port(6370).build();
            redisServer.start();
        } catch (Exception ex){
            //do nothing
        }
    }

    @Test
    void test_board_creation_service() throws Exception {
        //************************
        //          Given
        //************************
        //************************
        //          WHEN
        //************************
        MvcResult responseBody = mockMvc.perform(MockMvcRequestBuilders.post(CREATE_BOARD_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
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

        saveNewBoard(realPits, botPits, 0, 0);

        String requestBody = "{\"id\":\"50b66cc4-d64a-456b-b202-2c258100f057\",\"realPits\":[{\"id\":1,\"value\":6},{\"id\":2,\"value\":6},{\"id\":3,\"value\":6},{\"id\":4,\"value\":6},{\"id\":5,\"value\":6},{\"id\":6,\"value\":6}]," +
                "\"botPits\":[{\"id\":1,\"value\":6},{\"id\":2,\"value\":6},{\"id\":3,\"value\":6},{\"id\":4,\"value\":6},{\"id\":5,\"value\":6},{\"id\":6,\"value\":6}],\"botStorage\":0,\"realStorage\":0}";

        //************************
        //          WHEN
        //************************
        MvcResult responseBody = mockMvc.perform(MockMvcRequestBuilders.post(REAL_TO_BOT_PLAYER_MAKE_TURN_ENDPOINT+"/6")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String restResponse = responseBody.getResponse().getContentAsString();
        //************************
        //          THEN
        //************************
        // check rest response
        assertThat(restResponse).isNotEmpty();

        JSONAssert.assertEquals(restResponse, "{\"id\":\"50b66cc4-d64a-456b-b202-2c258100f057\",\"realPits\":[{\"id\":1,\"value\":7},{\"id\":2,\"value\":7},{\"id\":3,\"value\":7},{\"id\":4,\"value\":7},{\"id\":5,\"value\":7},{\"id\":6,\"value\":0}]," +
                "\"botPits\":[{\"id\":1,\"value\":0},{\"id\":2,\"value\":7},{\"id\":3,\"value\":7},{\"id\":4,\"value\":7},{\"id\":5,\"value\":7},{\"id\":6,\"value\":7}],\"botStorage\":1,\"realStorage\":1}", true);
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

        saveNewBoard(realPits, botPits, 18, 20);

        String requestBody = "{\"id\":\"50b66cc4-d64a-456b-b202-2c258100f057\",\"realPits\":[{\"id\":1,\"value\":1},{\"id\":2,\"value\":0},{\"id\":3,\"value\":0},{\"id\":4,\"value\":0},{\"id\":5,\"value\":4},{\"id\":6,\"value\":0}]," +
                "\"botPits\":[{\"id\":1,\"value\":0},{\"id\":2,\"value\":1},{\"id\":3,\"value\":1},{\"id\":4,\"value\":0},{\"id\":5,\"value\":0},{\"id\":6,\"value\":22}],\"botStorage\":18,\"realStorage\":20}";

        //************************
        //          WHEN
        //************************
        MvcResult responseBody = mockMvc.perform(MockMvcRequestBuilders.post(REAL_TO_BOT_PLAYER_MAKE_TURN_ENDPOINT+"/1")
                .content(requestBody)
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

        JSONAssert.assertEquals(restResponse, "{\"id\":\"50b66cc4-d64a-456b-b202-2c258100f057\",\"realPits\":[{\"id\":1,\"value\":0},{\"id\":2,\"value\":0},{\"id\":3,\"value\":0},{\"id\":4,\"value\":0},{\"id\":5,\"value\":4},{\"id\":6,\"value\":0}]," +
                "\"botPits\":[{\"id\":1,\"value\":0},{\"id\":2,\"value\":0},{\"id\":3,\"value\":1},{\"id\":4,\"value\":0},{\"id\":5,\"value\":0},{\"id\":6,\"value\":22}],\"botStorage\":20,\"realStorage\":20}", true);
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

        saveNewBoard(realPits, botPits, 18, 20);

        String requestBody = "{\"id\":\"50b66cc4-d64a-456b-b202-2c258100f057\",\"realPits\":[{\"id\":1,\"value\":1},{\"id\":2,\"value\":0},{\"id\":3,\"value\":0},{\"id\":4,\"value\":0},{\"id\":5,\"value\":4},{\"id\":6,\"value\":0}]," +
                "\"botPits\":[{\"id\":1,\"value\":0},{\"id\":2,\"value\":1},{\"id\":3,\"value\":1},{\"id\":4,\"value\":0},{\"id\":5,\"value\":0},{\"id\":6,\"value\":22}],\"botStorage\":20,\"realStorage\":18}";

        //************************
        //          WHEN
        //************************
        //************************
        //          THEN
        //************************
        mockMvc.perform(MockMvcRequestBuilders.post(REAL_TO_BOT_PLAYER_MAKE_TURN_ENDPOINT+"/2")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof KalahaWebException))
                .andExpect(result -> assertEquals("Chosen pit does not filled, please choose another pit!",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andReturn();
    }

    @Test
    void test_real_player_make_turn_and_choose_wrong_pit_id_service() throws Exception {
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

        saveNewBoard(realPits, botPits, 18, 20);

        String requestBody = "{\"id\":\"50b66cc4-d64a-456b-b202-2c258100f057\",\"realPits\":[{\"id\":1,\"value\":1},{\"id\":2,\"value\":0},{\"id\":3,\"value\":0},{\"id\":4,\"value\":0},{\"id\":5,\"value\":4},{\"id\":6,\"value\":0}]," +
                "\"botPits\":[{\"id\":1,\"value\":0},{\"id\":2,\"value\":1},{\"id\":3,\"value\":1},{\"id\":4,\"value\":0},{\"id\":5,\"value\":0},{\"id\":6,\"value\":22}],\"botStorage\":20,\"realStorage\":18}";

        //************************
        //          WHEN
        //************************
        //************************
        //          THEN
        //************************
        mockMvc.perform(MockMvcRequestBuilders.post(REAL_TO_BOT_PLAYER_MAKE_TURN_ENDPOINT+"/7")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof KalahaOutOfBandException))
                .andExpect(result -> assertEquals("Chosen pit should be between 1 to 6!",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andReturn();
    }

    @Test
    void test_bot_player_made_game_ended() throws Exception {
        //************************
        //          Given
        //************************
        //Board state
        HashMap<Integer, Integer> botPits = new HashMap<>() {{
            put(1, 1);
            put(2, 0);
            put(3, 0);
            put(4, 0);
            put(5, 0);
            put(6, 0);
        }};

        HashMap<Integer, Integer> realPits = new HashMap<>() {{
            put(1, 0);
            put(2, 1);
            put(3, 1);
            put(4, 0);
            put(5, 0);
            put(6, 22);
        }};

        saveNewBoard(realPits, botPits, 21, 20);

        String requestBody = "{\"id\":\"50b66cc4-d64a-456b-b202-2c258100f057\",\"botPits\":[{\"id\":1,\"value\":1},{\"id\":2,\"value\":0},{\"id\":3,\"value\":0},{\"id\":4,\"value\":0},{\"id\":5,\"value\":0},{\"id\":6,\"value\":0}]," +
                "\"realPits\":[{\"id\":1,\"value\":0},{\"id\":2,\"value\":1},{\"id\":3,\"value\":1},{\"id\":4,\"value\":0},{\"id\":5,\"value\":0},{\"id\":6,\"value\":22}],\"botStorage\":20,\"realStorage\":21}";

        //************************
        //          WHEN
        //************************
        MvcResult responseBody = mockMvc.perform(MockMvcRequestBuilders.post(REAL_TO_BOT_PLAYER_MAKE_TURN_ENDPOINT+"/3")
                .content(requestBody)
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

        JSONAssert.assertEquals(restResponse, "{\"id\":\"50b66cc4-d64a-456b-b202-2c258100f057\",\"realPits\":[{\"id\":1,\"value\":0},{\"id\":2,\"value\":0},{\"id\":3,\"value\":0},{\"id\":4,\"value\":0},{\"id\":5,\"value\":0},{\"id\":6,\"value\":0}]," +
                "\"botPits\":[{\"id\":1,\"value\":0},{\"id\":2,\"value\":0},{\"id\":3,\"value\":0},{\"id\":4,\"value\":0},{\"id\":5,\"value\":0},{\"id\":6,\"value\":0}],\"botStorage\":21,\"realStorage\":45}", true);
    }

    private void saveNewBoard(HashMap<Integer, Integer> realPits, HashMap<Integer, Integer> botPits, int realStorage, int botStorage) {
        Board board = Board.builder()
                .id("50b66cc4-d64a-456b-b202-2c258100f057")
                .realStorage(realStorage)
                .botStorage(botStorage)
                .realPits(realPits)
                .isRealTurn(true)
                .isBotTurn(true)
                .gameState(GameState.ACTIVE)
                .botPits(botPits).build();

        boardRepository.save(board);
    }
}
