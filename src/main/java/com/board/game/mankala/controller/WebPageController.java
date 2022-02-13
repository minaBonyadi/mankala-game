package com.board.game.mankala.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {
    @GetMapping("play-game")
    public String playGame() {
        return "play-game";
    }
}
