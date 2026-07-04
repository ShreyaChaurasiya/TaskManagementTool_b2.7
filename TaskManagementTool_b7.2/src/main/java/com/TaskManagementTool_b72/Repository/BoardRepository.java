package com.TaskManagementTool_b72.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementTool_b72.Entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long>{

    Optional<Board>findByProjectKey(String projectKey);

}

