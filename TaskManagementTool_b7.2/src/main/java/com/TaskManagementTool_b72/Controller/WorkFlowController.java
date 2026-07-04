package com.TaskManagementTool_b72.Controller;


import java.util.List;
import java.util.Set;
import com.TaskManagementTool_b72.Entity.WorkFlow;
import com.TaskManagementTool_b72.Enum.IssueStatus;
import com.TaskManagementTool_b72.Enum.Role;
import com.TaskManagementTool_b72.Service.WorkFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/work_flows")
@RequiredArgsConstructor
public class WorkFlowController {

    @Autowired
    private WorkFlowService workflowService;



    @PostMapping("/create")
    public ResponseEntity<WorkFlow> createWorkFlow(@RequestBody WorkFlow workFlow){
        return ResponseEntity.ok(workflowService.createWorkFlow(workFlow));
    }

    @GetMapping("/all")
    public ResponseEntity<List<WorkFlow>>getAllWorkFlow(){
        return ResponseEntity.ok(workflowService.listAllWork());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkFlow>getWorkByWorkFlowId(@PathVariable Long id){
        return ResponseEntity.ok(workflowService.getByWorkId(id));
    }

    @PutMapping("/update/{id}")

    public ResponseEntity<WorkFlow>update(@PathVariable Long id,@RequestBody WorkFlow update){
        return ResponseEntity.ok(workflowService.updtaeWorkFlow(id, update));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String>deleteWorkFlow(@PathVariable Long id){
        workflowService.deleteWork(id);
        return ResponseEntity.ok("work deleted");
    }
    @GetMapping("/transaction/{id}")
    public ResponseEntity<Boolean> allowedTransaction(@PathVariable Long id,
                                                      @RequestParam IssueStatus from,
                                                      @RequestBody Set<Role> userRole){
        return ResponseEntity.ok(workflowService.isTransactionsAllowed(id, from, from, userRole));
    }

    @PostMapping("/validate-transaction/{id}")
    public ResponseEntity<Boolean>validateTransactions(@PathVariable Long id,
                                                       @RequestParam IssueStatus from,
                                                       @RequestParam IssueStatus to,
                                                       @RequestBody Set<Role> userRole){
        boolean allowed= workflowService.isTransactionsAllowed(id, from, to, userRole);
        return ResponseEntity.ok(allowed);
    }
}
