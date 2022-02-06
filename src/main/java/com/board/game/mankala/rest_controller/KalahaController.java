package com.board.game.mankala.rest_controller;

import com.board.game.mankala.KalahaService;
import com.board.game.mankala.data.Board;
import com.board.game.mankala.data.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
//@Api()
public class KalahaController {

    private final KalahaService kalahaService;

    @GetMapping("/create-board")
    public ResponseEntity<Board> createGame() {
        return new ResponseEntity<>(kalahaService.createBoard(), HttpStatus.OK);
    }

    @PostMapping("/make-turn/{pitId}")
    public ResponseEntity<Board> makeTurn(@Valid @RequestBody BoardDto boardDto, @PathVariable int pitId){
        return new ResponseEntity<>(kalahaService.makeTurn(boardDto, pitId), HttpStatus.OK);
    }
}
