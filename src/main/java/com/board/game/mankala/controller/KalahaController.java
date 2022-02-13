package com.board.game.mankala.controller;

import com.board.game.mankala.service.MankalaService;
import com.board.game.mankala.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
//@Api()
public class KalahaController {

    private final MankalaService mankalaService;

    @PostMapping("/create-game")
    public ResponseEntity<BoardDto> createGame() {
        return new ResponseEntity<>(mankalaService.createGame(), HttpStatus.CREATED);
    }

    @PostMapping("/make-turn/{pitId}")
    public ResponseEntity<BoardDto> makeTurnRealPlayer(@Valid @RequestBody BoardDto boardDto, @PathVariable int pitId) {
        return new ResponseEntity<>(mankalaService.makeTurn(boardDto, pitId), HttpStatus.OK);
    }
}
