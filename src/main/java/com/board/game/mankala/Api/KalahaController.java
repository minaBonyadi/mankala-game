package com.board.game.mankala.Api;

import com.board.game.mankala.KalahaService;
import com.board.game.mankala.model.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("game")
@RequiredArgsConstructor
//@Api()
public class KalahaController {

    private final KalahaService kalahaService;

    @GetMapping("/create-board")
    public ResponseEntity<Board> createGame() {
        return new ResponseEntity<>(kalahaService.createBoard(), HttpStatus.OK);
    }

//    @PostMapping("/make-turn")
//    public ResponseEntity<Boolean> makeTurn(){
//        return new ResponseEntity<>(false, HttpStatus.OK);
//    }
}
