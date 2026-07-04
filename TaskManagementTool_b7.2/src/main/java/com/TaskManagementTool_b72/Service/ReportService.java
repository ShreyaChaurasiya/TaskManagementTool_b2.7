package com.TaskManagementTool_b72.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManagementTool_b72.Entity.Issue;
import com.TaskManagementTool_b72.Entity.Sprint;
import com.TaskManagementTool_b72.Enum.IssueStatus;
import com.TaskManagementTool_b72.Enum.SprintState;
import com.TaskManagementTool_b72.Repository.IssueRepository;
import com.TaskManagementTool_b72.Repository.SprintRepository;

@Service
public class ReportService {

    @Autowired
    private IssueRepository issueRepo;

    @Autowired
    private SprintRepository sprintRepo;



    public Map<String,Object>burnDownData(Long sprintId){

        Sprint sprint= sprintRepo.findById(sprintId).orElseThrow(()-> new RuntimeException("sprint not found"));
        List<Issue> issues= issueRepo.findBySprintId(sprintId);

        int totalTask= issues.size();

        Map<String,Object>chart= new LinkedHashMap<>();

        LocalDateTime startSprint= sprint.getStartDate();
        LocalDateTime endSprint= sprint.getEndDate() != null? sprint.getEndDate():LocalDateTime.now();

        for(LocalDateTime d=startSprint; !d.isAfter(endSprint);d=d.plusDays(1)) {


            int finishedTask= (int)issues
                    .stream()
                    .filter(i -> i.getIssueStatus() == IssueStatus.DONE)
                    .count();
            chart.put(d.toString(),totalTask-finishedTask );

        }

        Map<String,Object>response= new HashMap<>();
        response.put("sprintId",sprintId );
        response.put("burnDownData", chart);

        return response;

    }


    public Map<String,Object>velocity(Long projectId){

        List<Sprint>completed= sprintRepo.findByProjectId(projectId)
                .stream()
                .filter(s-> s.getSprintstate()== SprintState.COMPLETED)
                .collect(Collectors.toList());

        Map<String,Integer>velocity=new HashMap<>();

        for(Sprint sprint:completed) {
            int done= (int)issueRepo.findBySprintId(sprint.getId()).stream()
                    .filter(i->i.getIssueStatus()==IssueStatus.DONE).count();
            velocity.put(sprint.getSprintName(), done);

        }


        Map<String,Object>response=new HashMap<>();
        response.put("projectId", projectId);
        response.put("velocity",velocity );

        return response;

    }

    public Map<String,Object>sprintReport(Long id){

        List<Issue>issues= issueRepo.findBySprintId(id);

        long completed=issues.stream().filter(i-> i.getIssueStatus()==IssueStatus.DONE).count();

        long incomplete= issues.size()-completed;

        Map<String,Object>response= new HashMap<>();
        response.put("totalIssues", issues.size());
        response.put("completed", completed);
        response.put("incompleted", incomplete);

        return response;

    }


    public Map<String,Object>epicProgressReport(Long epicId){
        List<Issue>stories= issueRepo.findByEpicId(epicId);

        long doneEpic= stories.stream().filter(i->i.getIssueStatus()==IssueStatus.DONE).count();

        long inProgressEpic= stories.isEmpty()?0:(doneEpic*100/stories.size());


        Map<String,Object>response= new HashMap<>();

        response.put("epicId", epicId);
        response.put("totalStrories", stories.size());
        response.put("completedStories",doneEpic );
        response.put("InprogessStories", inProgressEpic);

        return response;

    }



    public Map<String,Object>workLoadReport(Long sprintId){

        List<Issue>issues= issueRepo.findBySprintId(sprintId);

        Map<String,Long>workLoad= issues.stream()
                .collect(Collectors.groupingBy(Issue::getAssigneeEmail,Collectors.counting()));


        Map<String,Object>response=new HashMap<>();
        response.put("workLoad", workLoad);


        return response;

    }

    public Map<String,Object>flowDiagramForReport(Long sprintId){
        List<Issue>issues= issueRepo.findBySprintId(sprintId);

        Map<String,Long>fdr= issues.stream()
                .collect(Collectors.groupingBy(issue-> issue.getIssueStatus().name(),Collectors.counting()));


        Map<String,Object>response= new HashMap<>();

        response.put("flowDiagram",fdr );

        return response;

    }
}

