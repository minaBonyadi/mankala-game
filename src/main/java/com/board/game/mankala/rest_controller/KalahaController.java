package com.board.game.mankala.rest_controller;

import com.board.game.mankala.service.KalahaService;
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

    @PostMapping("/real/make-turn/{pitId}")
    public ResponseEntity<BoardDto> makeTurnRealPlayer(@Valid @RequestBody BoardDto boardDto, @PathVariable int pitId){
        return new ResponseEntity<>(kalahaService.makeTurnToRealPlayer(boardDto, pitId), HttpStatus.OK);
    }

    @PostMapping("/bot/make-turn")
    public ResponseEntity<BoardDto> makeTurnBotPlayer(@Valid @RequestBody BoardDto boardDto){
        return new ResponseEntity<>(kalahaService.makeTurnToBotPlayer(boardDto), HttpStatus.OK);
    }

    @PostMapping("/check-game-ended")
    public ResponseEntity<BoardDto> checkGameEnded(@Valid @RequestBody BoardDto boardDto){
        return new ResponseEntity<>(kalahaService.checkGameEnded(boardDto), HttpStatus.OK);
    }
}
