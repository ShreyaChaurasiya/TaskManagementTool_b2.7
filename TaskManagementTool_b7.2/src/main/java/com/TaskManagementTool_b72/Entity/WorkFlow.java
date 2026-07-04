package com.TaskManagementTool_b72.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "work_flows")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(length = 5000)
    private String description;

    @OneToMany(
            mappedBy = "workFlow",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<WorkFlowTransaction> transactions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WorkFlowTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<WorkFlowTransaction> transactions) {
        this.transactions = transactions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
