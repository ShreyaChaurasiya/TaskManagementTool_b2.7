package com.TaskManagementTool_b72.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementTool_b72.Entity.Issue;
import com.TaskManagementTool_b72.Enum.IssueStatus;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    Optional<Issue> findByIssueKey(String issueKey);

    List<Issue> findByAssigneeEmail(String assigneeEmail);

    List<Issue> findByIssueStatus(IssueStatus issueStatus);

    List<Issue> findBySprintId(Long sprintId);

    List<Issue> findByEpicId(Long epicId);

    // For Backlog
    List<Issue> findByProjectIdAndSprintIdIsNullOrderByBackLogPosition(Long projectId);
}