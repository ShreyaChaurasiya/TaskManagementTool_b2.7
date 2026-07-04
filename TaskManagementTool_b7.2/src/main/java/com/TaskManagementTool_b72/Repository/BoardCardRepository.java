package com.TaskManagementTool_b72.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementTool_b72.Entity.BoardCards;

@Repository
public interface BoardCardRepository extends JpaRepository<BoardCards, Long> {

    List<BoardCards> findByBoardIdAndColumnIdOrderByPositionInOrd(
            Long boardId,
            Long columnId
    );

    long countByBoardIdAndColumnId(
            Long boardId,
            Long columnId
    );

    Optional<BoardCards> findByIssueId(Long issueId);
}