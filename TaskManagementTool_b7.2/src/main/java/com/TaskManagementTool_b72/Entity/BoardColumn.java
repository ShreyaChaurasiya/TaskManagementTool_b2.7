package com.TaskManagementTool_b72.Entity;

import com.TaskManagementTool_b72.Enum.IssueStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="board_columns",indexes={@Index(columnList="board_Id,positionInOrd")})

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardColumn {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    private String name;

    private IssueStatus statusKey;

    private Integer positionInOrd;
    private Integer wipLimit;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public IssueStatus getStatusKey() {
        return statusKey;
    }
    public void setStatusKey(IssueStatus statusKey) {
        this.statusKey = statusKey;
    }
    public Integer getPositionInOrd() {
        return positionInOrd;
    }
    public void setPositionInOrd(Integer positionInOrd) {
        this.positionInOrd = positionInOrd;
    }
    public Integer getWipLimit() {
        return wipLimit;
    }
    public void setWipLimit(Integer wipLimit) {
        this.wipLimit = wipLimit;
    }

}
