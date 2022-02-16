package com.board.game.mankala.component;

import com.board.game.mankala.config.MankalaPropertiesConfiguration;
import com.board.game.mankala.entity.Board;
import com.board.game.mankala.enumeration.StrategyName;
import com.board.game.mankala.strategy.PlayingStrategy;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.exception.MankalaWebException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class RealToBotPlayingStrategy implements PlayingStrategy {

    private final RealPlayerImpl realPlayer;
    private final BotPlayerImpl botPlayer;
    private final MankalaPropertiesConfiguration mankalaConfig;
    private final GameEnded gameEnded;

    @Override
    public StrategyName getStrategyName() { return StrategyName.REALTOBOT; }

    /**
     * play a game
     * @param board get all board data
     * @param pitId real player selected pit id
     * @param type REAL player (because real player starting a game)
     * @return board after effected by one of player move
     */
    @Override
    public Board play(Board board, int pitId, PlayerType type) {
        if (type.equals(PlayerType.BOT)) {
            return playByBotPlayer(board);
        }
        return playByRealPlayer(board, pitId);
    }

    /**
     *
     * @param board all board data
     * @param pitId selected pit id
     * @return board after get effected by sow which taken place in real player side
     */
    public Board playByRealPlayer(Board board, int pitId) {
        int chosenStone = board.getRealPits().get(pitId);

        if (chosenStone == mankalaConfig.getZero()) {
            log.error(String.format("Chosen pit does not filled %s", pitId));
            throw new MankalaWebException("Chosen pit does not filled, please choose another pit!");
        }

        Board boardResult = realPlayer.sow(board, pitId, chosenStone, PlayerType.REAL);
        return gameEnded.checkGameEnded(boardResult);
    }

    /**
     *
     * @param board all board data
     * @return board after get effected by sow which taken place in bot player side
     */
    public Board playByBotPlayer(Board board) {
        int chosenPitId = mankalaConfig.getBotRandomPitId();
        int chosenStone = board.getBotPits().get(chosenPitId);

        int counter = 0 ;
        while (chosenStone == 0 && counter <= mankalaConfig.getPitsIdMaxSize()){
            chosenPitId = mankalaConfig.getBotRandomPitId();
            chosenStone = board.getBotPits().get(chosenPitId);
            counter++;
        }

        Board boardResult = botPlayer.sow(board, chosenPitId, chosenStone, PlayerType.BOT);
        return gameEnded.checkGameEnded(boardResult);
    }
}
