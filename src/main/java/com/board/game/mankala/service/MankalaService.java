package com.board.game.mankala.service;

import com.board.game.mankala.component.RealToBotPlayingStrategy;
import com.board.game.mankala.config.MankalaPropertiesConfiguration;
import com.board.game.mankala.dto.BoardDto;
import com.board.game.mankala.entity.Board;
import com.board.game.mankala.enumeration.GameState;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.enumeration.StrategyName;
import com.board.game.mankala.impl.PlayingStrategy;
import com.board.game.mankala.repository.BoardRepository;
import com.board.game.mankala.exception.KalahaBoardNotFoundException;
import com.board.game.mankala.exception.KalahaOutOfBandException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class MankalaService {

    private final MankalaPropertiesConfiguration mankalaSetting;
    private final BoardRepository boardRepository;
    private final RealToBotPlayingStrategy realToBotPlayingStrategy;
    private final GameStrategyFactory gameStrategyFactory;

    private PlayingStrategy getGameStrategy(){
        return gameStrategyFactory.findStrategy(StrategyName.REALTOBOT);
    }

    public BoardDto createGame(){
        Map<Integer, Integer> botPlayer = new HashMap<>();
        Map<Integer, Integer> realPlayer = new HashMap<>();

        createBoard(botPlayer, realPlayer);

        boardRepository.save(Board.builder()
               .realStorage(mankalaSetting.getStorageMinValue())
               .botStorage(mankalaSetting.getStorageMinValue())
               .botPits(botPlayer)
               .realPits(realPlayer)
               .gameState(GameState.ACTIVE)
               .build());

       return BoardDto.builder()
                .realStorage(mankalaSetting.getStorageMinValue())
                .botStorage(mankalaSetting.getStorageMinValue())
                .botPits(botPlayer)
                .realPits(realPlayer)
                .build();
    }

    public BoardDto makeTurn(BoardDto boardDto , int pitId){
        makeTurnToRealPlayer(boardDto, pitId);
        makeTurnToBotPlayer(boardDto);

        Board boardResult = boardRepository.findById(boardDto.getId()).orElseThrow(KalahaBoardNotFoundException::new);
        checkGameEnded(boardResult);

        return BoardDto.builder()
                .id(boardResult.getId())
                .realPits(boardResult.getRealPits())
                .botPits(boardResult.getBotPits())
                .realStorage(boardResult.getRealStorage())
                .botStorage(boardResult.getBotStorage())
                .build();
    }

    public void makeTurnToRealPlayer(BoardDto boardDto , int pitId) {
        validateChosenPitId(pitId);  // validate chosen pit Id

        Board board = boardRepository.findById(boardDto.getId())
                .orElseThrow(KalahaBoardNotFoundException::new);

        getGameStrategy().play(board, pitId, PlayerType.REAL);
    }

    private void validateChosenPitId(int pitId) {
        if (pitId > mankalaSetting.getPitsIdMaxSize()|| pitId < mankalaSetting.getPitsIdMinSize()){
            throw new KalahaOutOfBandException(String.format("Chosen pit should be between %s to %s!",
                    mankalaSetting.getPitsIdMinSize(), mankalaSetting.getPitsIdMaxSize()));
        }
    }

    public void makeTurnToBotPlayer(BoardDto boardDto) {
        Board board = boardRepository.findById(boardDto.getId())
                .orElseThrow(KalahaBoardNotFoundException::new);

        getGameStrategy().play(board, mankalaSetting.getZero(), PlayerType.BOT);
    }

    private void createBoard(Map<Integer, Integer> botPlayer, Map<Integer, Integer> realPlayer) {
        int counter = 0;
        int botPitId = mankalaSetting.getPitsIdMinSize();
        int realPitId = mankalaSetting.getPitsIdMinSize();

        while(counter < mankalaSetting.getAllPitsCount()) {

            if (counter < mankalaSetting.getEachPlayerPitsCount()) {
                botPlayer.put(botPitId++, mankalaSetting.getPitsMaxValueLimit());

            }else {
                realPlayer.put(realPitId++, mankalaSetting.getPitsMaxValueLimit());
            }
            counter++;
        }
    }

    private void checkGameEnded(Board board) {
        if (board.getRealPits().values().stream().allMatch(value -> value == 0) ||
                board.getBotPits().values().stream().allMatch(value -> value == 0)) {

            board.setRealStorage(board.getRealStorage() + board.getRealPits().values().stream().mapToInt(Integer::intValue).sum());
            board.getRealPits().replaceAll((k, v) -> v = 0);
            board.setBotStorage(board.getBotStorage() + board.getBotPits().values().stream().mapToInt(Integer::intValue).sum());
            board.getBotPits().replaceAll((k, v) -> v = 0);
            board.setGameState(GameState.STOPPED);
            boardRepository.save(board);
        }else {
            log.info("This mankala game has not ended yet!");
        }
    }
}
