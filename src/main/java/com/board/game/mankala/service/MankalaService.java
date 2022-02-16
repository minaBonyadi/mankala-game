package com.board.game.mankala.service;

import com.board.game.mankala.config.MankalaPropertiesConfiguration;
import com.board.game.mankala.dto.board.BoardDto;
import com.board.game.mankala.dto.board.StoneDto;
import com.board.game.mankala.entity.Board;
import com.board.game.mankala.enumeration.GameState;
import com.board.game.mankala.enumeration.PlayerType;
import com.board.game.mankala.enumeration.StrategyName;
import com.board.game.mankala.exception.KalahaIsTheWrongTurnException;
import com.board.game.mankala.strategy.PlayingStrategy;
import com.board.game.mankala.repository.BoardRepository;
import com.board.game.mankala.exception.KalahaBoardNotFoundException;
import com.board.game.mankala.exception.KalahaOutOfBandException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class MankalaService {

    private final MankalaPropertiesConfiguration mankalaSetting;
    private final BoardRepository boardRepository;
    private final GameStrategyFactory gameStrategyFactory;

    private PlayingStrategy getGameStrategy(){
        return gameStrategyFactory.findStrategy(StrategyName.REALTOBOT);
    }

    public BoardDto createGame(){
        Board board = createBoard();
        log.info(String.format("A new game is create with id {%s}", board.getId()));

        return BoardDto.builder()
                .id(board.getId())
                .realStorage(mankalaSetting.getStorageMinValue())
                .botStorage(mankalaSetting.getStorageMinValue())
                .botPits(getBotPitsInDto(board.getBotPits()))
                .realPits(getRealPitsInDto(board.getBotPits()))
                .build();
    }

    public BoardDto makeTurn(BoardDto boardDto , int pitId) {
        makeTurnToRealPlayer(boardDto, pitId);
        makeTurnToBotPlayer(boardDto);
        Board boardResult = checkGameEnded(boardDto);

        return BoardDto.builder()
                .id(boardResult.getId())
                .realPits(getRealPitsInDto(boardResult.getRealPits()))
                .botPits(getBotPitsInDto(boardResult.getBotPits()))
                .realStorage(boardResult.getRealStorage())
                .botStorage(boardResult.getBotStorage())
                .build();
    }

    public void makeTurnToRealPlayer(BoardDto boardDto , int pitId) {
        validateChosenPitId(pitId);  // validate chosen pit Id

        Board board = boardRepository.findById(boardDto.getId())
                .orElseThrow(KalahaBoardNotFoundException::new);

        if (!board.isRealTurn()) {
            makeTurnToBotPlayer(boardDto);
            throw new KalahaIsTheWrongTurnException("sorry, It is bot player turning again!");
        }
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

        if (!board.isBotTurn()) {
            throw new KalahaIsTheWrongTurnException("It is real player turning again!");
        }
        getGameStrategy().play(board, mankalaSetting.getZero(), PlayerType.BOT);
    }

    private Board createBoard() {
        Map<Integer, Integer> botPlayer = new HashMap<>();
        Map<Integer, Integer> realPlayer = new HashMap<>();

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
        return boardRepository.save(Board.builder()
                .realStorage(mankalaSetting.getStorageMinValue())
                .botStorage(mankalaSetting.getStorageMinValue())
                .botPits(botPlayer)
                .realPits(realPlayer)
                .isBotTurn(true)
                .isRealTurn(true)
                .gameState(GameState.ACTIVE)
                .build());
    }

    private Board checkGameEnded(BoardDto boardDto) {
        Board board = boardRepository.findById(boardDto.getId()).orElseThrow(KalahaBoardNotFoundException::new);

        if (board.getRealPits().values().stream().allMatch(value -> value == 0) ||
                board.getBotPits().values().stream().allMatch(value -> value == 0)) {

            board.setRealStorage(board.getRealStorage() + board.getRealPits().values().stream().mapToInt(Integer::intValue).sum());
            board.getRealPits().replaceAll((k, v) -> v = 0);
            board.setBotStorage(board.getBotStorage() + board.getBotPits().values().stream().mapToInt(Integer::intValue).sum());
            board.getBotPits().replaceAll((k, v) -> v = 0);
            board.setGameState(GameState.STOPPED);
        }else {
            log.info("This mankala game has not ended yet!");
        }
        return boardRepository.save(board);
    }

    private List<StoneDto> getRealPitsInDto(Map<Integer, Integer> boardRealPits) {
        List<StoneDto> realPits = new ArrayList<>();
        boardRealPits.forEach((key, value)-> realPits.add(new StoneDto(key, value)));
        return realPits;
    }

    private List<StoneDto> getBotPitsInDto(Map<Integer, Integer> boardBotPits){
        List<StoneDto> botPits = new ArrayList<>();
        boardBotPits.forEach((key, value)-> botPits.add(new StoneDto(key, value)));
        return botPits;
    }

}
