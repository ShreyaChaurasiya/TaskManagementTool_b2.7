package com.TaskManagementTool_b72.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementTool_b72.Entity.Sprint;
import com.TaskManagementTool_b72.Enum.SprintState;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {

    List<Sprint> findByProjectId(Long projectId);

    List<Sprint> findBySprintstate(SprintState sprintstate);

}




