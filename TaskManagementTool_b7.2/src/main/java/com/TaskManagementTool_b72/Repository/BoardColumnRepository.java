package com.TaskManagementTool_b72.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementTool_b72.Entity.BoardColumn;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {

    List<BoardColumn> findByBoardIdOrderByPositionInOrd(Long boardId);
}

