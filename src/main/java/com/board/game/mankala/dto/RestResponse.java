package com.board.game.mankala.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestResponse {
    private RestResponseType code;
    private String message;
}
