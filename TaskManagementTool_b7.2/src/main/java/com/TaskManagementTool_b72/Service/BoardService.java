package com.TaskManagementTool_b72.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TaskManagementTool_b72.Entity.Board;
import com.TaskManagementTool_b72.Entity.BoardCards;
import com.TaskManagementTool_b72.Entity.BoardColumn;
import com.TaskManagementTool_b72.Entity.Issue;
import com.TaskManagementTool_b72.Enum.IssueStatus;
import com.TaskManagementTool_b72.Repository.BoardCardRepository;
import com.TaskManagementTool_b72.Repository.BoardColumnRepository;
import com.TaskManagementTool_b72.Repository.BoardRepository;
import com.TaskManagementTool_b72.Repository.IssueRepository;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepo;


    @Autowired
    private BoardColumnRepository boardColumnRepo;


    @Autowired
    private BoardCardRepository boardCardRepo;

    @Autowired
    private IssueRepository issueRepo;




    public Board createBoard(Board board) {
        return boardRepo.save(board);
    }


    public Board findById(Long id){
        return boardRepo.findById(id).orElseThrow(()-> new RuntimeException("board not found"));
    }


    public List<BoardColumn> getColumn(Long boardId){

        return boardColumnRepo.findByBoardIdOrderByPositionInOrd(boardId);
    }

    public List<BoardCards>getCardsAndColumn(Long boardId,Long columnId){

        return boardCardRepo.findByBoardIdAndColumnIdOrderByPositionInOrd(boardId, columnId);
    }

    @Transactional
    public BoardCards addIssueToBoard(Long boardId,Long columnId,Long issueId) {

        Issue issue= issueRepo.findById(issueId).orElseThrow(()-> new RuntimeException("issue not found"));

        boardCardRepo.findByIssueId(issueId).ifPresent(boardCardRepo::delete);

        BoardColumn column= boardColumnRepo.findById(columnId).orElseThrow(()-> new RuntimeException("column not found"));

        if(column.getWipLimit()!= null && column.getWipLimit()>0) {
            long count= boardCardRepo.countByBoardIdAndColumnId(boardId, columnId);

            if(count>=column.getWipLimit()) {
                throw new RuntimeException("wip limit reached for column:"+ column.getName());
            }
        }



        List<BoardCards>existing= boardCardRepo.findByBoardIdAndColumnIdOrderByPositionInOrd(boardId, columnId);

        int position= existing.size();


        BoardCards cards= new BoardCards();
        cards.setBoardId(boardId);
        cards.setColumn(column);
        cards.setIssueId(issueId);
        cards.setPositionInOrd(position);
        boardCardRepo.save(cards);


//		if(column.getStatusKey()!=null) {
//
//			issue.setIssueStatus(Enum.valueOf(IssueStatus.class, column.getStatusKey()));
//			issue.setIssueStatus(Enum.valueOf(com.TaskManagementTool_b72.Enum.IssueStatus.class,column.getStatusKey()));
//			issueRepo.save(issue);
//
//		}
//
//
//

        return cards;

    }



    @Transactional
    public void moveCard(Long boardId,Long cardId,Long colulmId,int toPosition,String performBy) {

        BoardCards card= boardCardRepo.findById(cardId).orElseThrow(()-> new RuntimeException("Cards not found"));
        BoardColumn from= card.getColumn();
        BoardColumn to= boardColumnRepo.findById(colulmId).orElseThrow(()-> new RuntimeException("Column not found"));

        if(to.getWipLimit() !=null && to.getWipLimit()>0) {
            long count= boardCardRepo.countByBoardIdAndColumnId(boardId, colulmId);

            if(!Objects.equals(from.getId(),to.getId()) && count >= to.getWipLimit()) {
                throw new RuntimeException("Wip limits exceeded for colmn:"+to.getName());
            }
        }

        List<BoardCards>fromList= boardCardRepo.findByBoardIdAndColumnIdOrderByPositionInOrd(boardId, from.getId());

        for(BoardCards c:fromList) {

            if(c.getPositionInOrd()>card.getPositionInOrd()) {
                c.setPositionInOrd(c.getPositionInOrd()-1);
                boardCardRepo.save(c);
            }
        }


        List<BoardCards>toList= boardCardRepo.findByBoardIdAndColumnIdOrderByPositionInOrd(boardId, to.getId());

        for(BoardCards c: toList) {
            if(c.getPositionInOrd()>= toPosition) {
                c.setPositionInOrd(c.getPositionInOrd()+1);
                boardCardRepo.save(c);
            }
        }

        card.setColumn(to);
        card.setPositionInOrd(toPosition);
        boardCardRepo.save(card);


        issueRepo.findById(card.getIssueId()).ifPresent(issue->{
            if(to.getStatusKey()!=null) {
                issue.setIssueStatus(IssueStatus.OPEN);
                issueRepo.save(issue);
            }
        });

    }


    @Transactional
    public void recordColumn(Long boardId, Long columnId,List<Long>orderedCardId) {

        int pos=0;
        for(Long cardId : orderedCardId) {
            BoardCards card= boardCardRepo.findById(cardId).orElseThrow(()-> new RuntimeException("card no found"));

            card.setPositionInOrd(pos++);
            boardCardRepo.save(card);
        }
    }

    @Transactional
    public void startSprint(Long sprintId) {

    }

    @Transactional
    public void closeSprint(Long sprintId) {

    }
}

