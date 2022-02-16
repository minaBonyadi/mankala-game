package com.board.game.mankala.controller;

import com.board.game.mankala.dto.board.BoardDto;
import com.board.game.mankala.service.MankalaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class MankalaController {

    private final MankalaService mankalaService;

    @ApiOperation(value = "Create a new game", response = BoardDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully returned a new board")})
    @PostMapping("/create")
    public ResponseEntity<BoardDto> createGame() {
        return new ResponseEntity<>(mankalaService.createGame(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Make turn by real player", response = BoardDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned board by effected with both real and bot player move"),
            @ApiResponse(code = 406, message = "It is real player turning again!"),
            @ApiResponse(code = 406, message = "sorry, It is bot player turning again!"),
            @ApiResponse(code = 400, message = "Can not find the previous value of this pit"),
            @ApiResponse(code = 400, message = "Chosen pit does not filled, please choose another pit!"),
            @ApiResponse(code = 404, message = "Board not found")})
    @PostMapping("/make-turn/{pitId}")
    public ResponseEntity<BoardDto> makeTurnRealPlayer(@Valid @RequestBody BoardDto boardDto, @PathVariable int pitId) {
        return new ResponseEntity<>(mankalaService.makeTurn(boardDto, pitId), HttpStatus.OK);
    }
}
