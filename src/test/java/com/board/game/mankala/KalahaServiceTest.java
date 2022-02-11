package com.board.game.mankala;

import com.board.game.mankala.data.BoardRepository;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class KalahaServiceTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private BoardRepository boardRepository;

    private static final String CREATE_BOARD_ENDPOINT = "/game/create-board";

    @Test
    @Disabled
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

        //************************
        //          THEN
        //************************
        // check rest response
        assertThat(responseBody.getResponse().getContentAsString()).isNotEmpty();
//        assertThat(responseBody.getResponse().getContentAsString()).contains("True");
    }

}
