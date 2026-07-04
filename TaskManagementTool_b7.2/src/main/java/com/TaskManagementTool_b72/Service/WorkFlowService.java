package com.TaskManagementTool_b72.Service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TaskManagementTool_b72.Entity.WorkFlow;
import com.TaskManagementTool_b72.Entity.WorkFlowTransaction;
import com.TaskManagementTool_b72.Enum.IssueStatus;
import com.TaskManagementTool_b72.Enum.Role;
import com.TaskManagementTool_b72.Repository.WorkFlowRepository;
import com.TaskManagementTool_b72.Repository.WorkFlowTransactionRepository;

@Service
public class WorkFlowService {

    @Autowired
    private WorkFlowRepository workflowRepo;


    @Autowired
    private WorkFlowTransactionRepository workflowTransactionRepo;

    @Transactional
    public WorkFlow createWorkFlow(WorkFlow wf) {

        if (wf.getTransactions() != null) {
            wf.getTransactions().forEach(t -> t.setWorkFlow(wf));
        }

        return workflowRepo.save(wf);
    }

    public List<WorkFlow>listAllWork(){
        return workflowRepo.findAll();
    }

    public WorkFlow getByWorkId(Long id) {
        return workflowRepo.findById(id).orElseThrow(()-> new RuntimeException("workId not found"+id));
    }


    @Transactional
    public WorkFlow updtaeWorkFlow(Long id,WorkFlow update) {

        WorkFlow wf= getByWorkId(id);

        wf.setName(update.getName());
        wf.setDescription(update.getDescription());
        wf.getTransactions().clear();

        if(update.getTransactions() !=null) {
            for(WorkFlowTransaction t: update.getTransactions()) {
                t.setWorkFlow(wf);
                wf.getTransactions().add(t);
            }
        }

        return workflowRepo.save(wf);
    }


    @Transactional
    public void deleteWork(Long id) {
        workflowRepo.deleteById(id);
    }

    public boolean isTransactionsAllowed(Long workFlowId,IssueStatus from ,IssueStatus to ,Set<Role>userRole) {

        List<WorkFlowTransaction> transactions =
                workflowTransactionRepo.findByWorkFlowIdAndFrom(workFlowId, from);

        for(WorkFlowTransaction t: transactions) {

            if(!t.getTo().equals(to)) {
                continue;
            }

            if(t.getAllowedRole()==null) {
                return true;
            }

            for (Role role : userRole) {
                if (role.equals(t.getAllowedRole())) {
                    return true;
                }
            }

            return false;
        }
        return false;
    }
}

