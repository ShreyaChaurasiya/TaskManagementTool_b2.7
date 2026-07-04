package com.TaskManagementTool_b72.Entity;

import com.TaskManagementTool_b72.Enum.IssueStatus;
import com.TaskManagementTool_b72.Enum.Role;

import jakarta.persistence.*;



@Entity
@Table(name="work_flows_transactions")
public class WorkFlowTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_status")
    @Enumerated(EnumType.STRING)
    private IssueStatus from;

    @Column(name = "to_status")
    @Enumerated(EnumType.STRING)
    private IssueStatus to;

    private String actionName;

    @Enumerated(EnumType.STRING)
    private Role allowedRole;

    @ManyToOne
    @JoinColumn(name = "workflow_id")
    private WorkFlow workFlow;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public IssueStatus getFrom() {
        return from;
    }


    public void setFrom(IssueStatus from) {
        this.from = from;
    }


    public IssueStatus getTo() {
        return to;
    }


    public void setTo(IssueStatus to) {
        this.to = to;
    }


    public String getActionName() {
        return actionName;
    }


    public void setActionName(String actionName) {
        this.actionName = actionName;
    }


    public Role getAllowedRole() {
        return allowedRole;
    }


    public void setAllowedRole(Role allowedRole) {
        this.allowedRole = allowedRole;
    }


    public WorkFlow getWorkFlow() {
        return workFlow;
    }


    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

}
