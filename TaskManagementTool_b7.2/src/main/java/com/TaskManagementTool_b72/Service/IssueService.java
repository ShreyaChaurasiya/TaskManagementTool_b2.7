package com.TaskManagementTool_b72.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TaskManagementTool_b72.Entity.Issue;
import com.TaskManagementTool_b72.Entity.IssueComment;
import com.TaskManagementTool_b72.Entity.Sprint;
import com.TaskManagementTool_b72.Enum.IssuePriority;
import com.TaskManagementTool_b72.Enum.IssueStatus;
import com.TaskManagementTool_b72.Enum.IssueType;
import com.TaskManagementTool_b72.Enum.SprintState;
import com.TaskManagementTool_b72.Repository.IssueCommentRepository;
import com.TaskManagementTool_b72.Repository.IssueRepository;
import com.TaskManagementTool_b72.Repository.SprintRepository;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepo;


    @Autowired
    private IssueCommentRepository issueCommentRepo;

    @Autowired
    private SprintRepository sprintRepo;




    private String generatedKey(Long id) {
        return "PROJ-"+id;
    }


    @Transactional
    public Issue createIssue(Issue issue) {

        issue.setIssueType(
                issue.getIssueType() != null
                        ? issue.getIssueType()
                        : IssueType.TASK);

        issue.setPriority(
                issue.getPriority() != null
                        ? issue.getPriority()
                        : IssuePriority.MEDIUM);

        issue.setIssueStatus(IssueStatus.OPEN);

        // Set issue key before first save
        issue.setIssueKey("PROJ-" + System.currentTimeMillis());

        return issueRepo.save(issue);
    }

    public List<Issue>findIssueByAssigneeEmail(String userOfficialEmail){
        return issueRepo.findByAssigneeEmail(userOfficialEmail);
    }


    public Issue findIssueById(Long id) {

        return issueRepo.findById(id).orElseThrow(()-> new RuntimeException("Issue notfound") );
    }

    public List<Issue>findIssueBySprint(Long sprintId){

        return issueRepo.findBySprintId(sprintId);
    }



    @Transactional
    public Issue updateIssueStatus(Long id,IssueStatus issueStatus) {

        Issue issue= findIssueById(id);

//		IssueStatus newIssueStatus;

        if(issueStatus==null ) {
            throw new RuntimeException("Status can not be null");
        }


        issue.setIssueStatus(issueStatus);
        return issueRepo.save(issue);

    }


    @Transactional
    public IssueComment addComment(Long issueId,String authorEmail,String body) {


        Issue issue= findIssueById(issueId);

        IssueComment comment= new IssueComment();

        comment.setIssueId(issue.getId());
        comment.setAuthorEmail(authorEmail);
        comment.setBody(body);

        return issueCommentRepo.save(comment);
    }


    @Transactional
    public Sprint createSprint(Sprint sprint) {
        if(sprint.getSprintstate()==null) {
            sprint.setSprintstate(SprintState.PLANNED);
        }
        return sprintRepo.save(sprint);
    }

    public List<Issue>getIssueBySprint(Long sprintId){
        return issueRepo.findBySprintId(sprintId);
    }


    public List<Issue>search(Map<String,String>filters){

        List<Issue>issues= issueRepo.findAll();
        if(filters.containsKey("assigneeEmail")) {
            String email= filters.get("assigneeEmail");
            issues=issues.stream().filter(i-> email.equalsIgnoreCase(i.getAssigneeEmail())).collect(Collectors.toList());
//			return getIssueByAssigneeEmail(filters.get("assigneeEmail"));
        }


        if(filters.containsKey("IssueStatus")) {
            try {
                IssueStatus status= IssueStatus.valueOf(filters.get("issueStatus"));
                return issueRepo.findByIssueStatus(status);
            } catch (Exception e) {
                throw new RuntimeException("invalid status in filter");
            }
        }

        if(filters.containsKey("sprint")) {
            Long sprintId= Long.valueOf(filters.get("sprint"));
            return getIssueBySprint(sprintId);
        }

        return issueRepo.findAll();
    }



}

