package com.board.game.mankala.component;

import com.board.game.mankala.config.MankalaPropertiesConfiguration;
import com.board.game.mankala.dto.board.BoardDto;
import com.board.game.mankala.entity.Board;
import com.board.game.mankala.enumeration.GameState;
import com.board.game.mankala.enumeration.StrategyName;
import com.board.game.mankala.impl.PlayingStrategy;
import com.board.game.mankala.repository.BoardRepository;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.exception.KalahaBoardNotFoundException;
import com.board.game.mankala.exception.KalahaWebException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RealToBotPlayingStrategy implements PlayingStrategy {

    private final BoardRepository boardRepository;
    private final RealPlayerImpl realPlayer;
    private final BotPlayerImpl botPlayer;
    private final MankalaPropertiesConfiguration kalahaSetting;

    @Override
    public Board play(Board board, int pitId, PlayerType type) {
        if (type.equals(PlayerType.BOT)) {
            return playByBotPlayer(board);
        }
        return playByRealPlayer(board, pitId);
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.REALTOBOT;
    }

    public Board playByRealPlayer(Board board, int pitId) {
        int chosenPitValue = board.getRealPits().get(pitId);

        if (chosenPitValue == kalahaSetting.getZero()) {
            throw new KalahaWebException("Chosen pit does not filled, please choose another pit!");
        }

        realPlayer.sow(board, pitId, chosenPitValue, PlayerType.REAL);
        return boardRepository.findById(board.getId()).orElseThrow(KalahaBoardNotFoundException::new);
    }

    public Board playByBotPlayer(Board board) {
        int chosenPitId = kalahaSetting.getBotRandomPitId();
        int chosenPitValue = board.getBotPits().get(chosenPitId);

        int counter = 0 ;
        while (chosenPitValue == 0 && counter <= kalahaSetting.getPitsIdMaxSize()){
            chosenPitId = kalahaSetting.getBotRandomPitId();
            chosenPitValue = board.getBotPits().get(chosenPitId);
            counter++;
        }

        botPlayer.sow(board, chosenPitId, chosenPitValue, PlayerType.BOT);
        return boardRepository.findById(board.getId()).orElseThrow(KalahaBoardNotFoundException::new);
    }
}
