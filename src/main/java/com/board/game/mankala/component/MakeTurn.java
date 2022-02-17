package com.board.game.mankala.component;

import com.board.game.mankala.config.MankalaPropertiesConfiguration;
import com.board.game.mankala.dto.board.BoardDto;
import com.board.game.mankala.entity.Board;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.enumeration.StrategyName;
import com.board.game.mankala.exception.MankalaBoardNotFoundException;
import com.board.game.mankala.exception.MankalaIsTheWrongTurnException;
import com.board.game.mankala.exception.MankalaOutOfBandException;
import com.board.game.mankala.repository.BoardRepository;
import com.board.game.mankala.service.GameStrategyFactory;
import com.board.game.mankala.strategy.PlayingStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class MakeTurn {

    private final BoardRepository boardRepository;
    private final GameStrategyFactory gameStrategyFactory;
    private final MankalaPropertiesConfiguration mankalaSetting;


    private PlayingStrategy getGameStrategy(){
        return gameStrategyFactory.findStrategy(StrategyName.REALTOBOT);
    }

    /**
     *
     * @param gameId string id of current game
     * @param pitId real player chosen pit id
     */
    public void makeTurn(String gameId , int pitId) {
        makeTurnToRealPlayer(gameId, pitId);
        makeTurnToBotPlayer(gameId);
    }

    /**
     *
     * @param gameId string id of current game
     * @param pitId real player chosen pit id
     */
    private void makeTurnToRealPlayer(String gameId , int pitId) {
        validateChosenPitId(pitId);  // validate chosen pit Id

        Board board = boardRepository.findById(gameId).orElseThrow(MankalaBoardNotFoundException::new);

        if (!board.isRealTurn()) {
            makeTurnToBotPlayer(gameId);
            throw new MankalaIsTheWrongTurnException("sorry, It is bot player turning again!");
        }
        getGameStrategy().play(board, pitId, PlayerType.REAL);
    }

    /**
     *  validate real player pit id it should be between one to six
     * @param pitId string id of current game
     */
    private void validateChosenPitId(int pitId) {
        if (pitId > mankalaSetting.getPitsIdMaxSize() || pitId < mankalaSetting.getPitsIdMinSize()) {
            throw new MankalaOutOfBandException(String.format("Chosen pit should be between %s to %s!",
                    mankalaSetting.getPitsIdMinSize(), mankalaSetting.getPitsIdMaxSize()));
        }
    }

    /**
     *  make turn taken place in robot player side
     * @param gameId string id of current game
     */
    private void makeTurnToBotPlayer(String gameId) {
        Board board = boardRepository.findById(gameId)
                .orElseThrow(MankalaBoardNotFoundException::new);

        if (board.isBotTurn()) {
            getGameStrategy().play(board, mankalaSetting.getZero(), PlayerType.BOT);
        }else {
            log.warn("It is real player turning again!");
        }
    }
}
