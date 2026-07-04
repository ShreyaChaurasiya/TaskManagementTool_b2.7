package com.TaskManagementTool_b72.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="board_cards",indexes= {@Index(columnList="board_Id,column_Id,positionInOrd")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardCards {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="column_Id")
    private BoardColumn column;

    private Long issueId;
    private Long boardId;
    private Integer positionInOrd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BoardColumn getColumn() {
        return column;
    }

    public void setColumn(BoardColumn column) {
        this.column = column;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Integer getPositionInOrd() {
        return positionInOrd;
    }

    public void setPositionInOrd(Integer positionInOrd) {
        this.positionInOrd = positionInOrd;
    }

}

