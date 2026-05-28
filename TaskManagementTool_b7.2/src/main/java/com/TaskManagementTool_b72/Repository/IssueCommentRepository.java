package com.TaskManagementTool_b72.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementTool_b72.Entity.IssueComment;

@Repository
public interface IssueCommentRepository extends JpaRepository<IssueComment, Long> {

    List<IssueComment> findByIssueIdOrderByCreatedAtAsc(Long issueId);

}

