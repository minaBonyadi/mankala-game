package com.board.game.mankala.component;

import com.board.game.mankala.config.KalahaPropertiesConfiguration;
import com.board.game.mankala.data.Board;
import com.board.game.mankala.repository.BoardRepository;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.enumeration.StrategyName;
import com.board.game.mankala.exception.KalahaBoardNotFoundException;
import com.board.game.mankala.exception.KalahaWebException;
import com.board.game.mankala.impl.PlayingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealToBotPlayingStrategy implements PlayingStrategy{

    private final BoardRepository boardRepository;
    private final RealPlayerImpl realPlayer;
    private final BotPlayerImpl botPlayer;
    private final KalahaPropertiesConfiguration kalahaSetting;

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.REALTOBOT;
    }

    public Board playByRealPlayer(Board board, int pitId) {
        int chosenPitValue = board.getRealPits().get(pitId);

        if (chosenPitValue == kalahaSetting.getZero()) {
            throw new KalahaWebException("Chosen pit does not filled, please choose another pit!");
        }

        getBoardAfterRealPlayerMove(board, pitId, chosenPitValue);
        return boardRepository.findById(board.getId()).orElseThrow(KalahaBoardNotFoundException::new);
    }

    public Board playByBotPlayer(Board board) {
        int chosenPitValue = kalahaSetting.getBotRandomPitId();

        while (chosenPitValue == 0){
            chosenPitValue = kalahaSetting.getBotRandomPitId();
        }
        getBoardAfterBotPlayerMove(board, chosenPitValue);
        return boardRepository.findById(board.getId()).orElseThrow(KalahaBoardNotFoundException::new);
    }

    private void getBoardAfterRealPlayerMove(Board board, int pitId, int pitValue) {
        realPlayer.sow(board, pitId, pitValue, PlayerType.REAL);
    }

    private void getBoardAfterBotPlayerMove(Board board, int pitId){
        int pitValue = board.getBotPits().get(pitId);
        botPlayer.sow(board, pitId, pitValue, PlayerType.BOT);
    }

    public Board checkGameEnded(Board board){
        if (board.getRealPits().values().stream().allMatch(value -> value == 0) ||
                board.getBotPits().values().stream().allMatch(value -> value == 0)) {

            board.setRealStorage(board.getRealStorage() + board.getRealPits().values().stream().mapToInt(Integer::intValue).sum());
            board.getRealPits().replaceAll((k, v) -> v = 0);
            board.setBotStorage(board.getBotStorage() + board.getBotPits().values().stream().mapToInt(Integer::intValue).sum());
            board.getBotPits().replaceAll((k, v) -> v = 0);
            return boardRepository.save(board);
        }else {
            throw new KalahaWebException("Kalaha game board has not ended yet!");
        }
    }
}
