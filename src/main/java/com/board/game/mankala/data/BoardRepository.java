package com.board.game.mankala.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends CrudRepository<Board, String> {
    @Override
    List<Board> findAll();
}
