package com.TaskManagementTool_b72.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementTool_b72.Entity.WorkFlowTransaction;
import com.TaskManagementTool_b72.Enum.IssueStatus;

@Repository
public interface WorkFlowTransactionRepository
        extends JpaRepository<WorkFlowTransaction, Long> {

    List<WorkFlowTransaction> findByWorkFlowId(Long workFlowId);

    List<WorkFlowTransaction> findByWorkFlowIdAndFrom(
            Long workFlowId,
            IssueStatus from
    );
}