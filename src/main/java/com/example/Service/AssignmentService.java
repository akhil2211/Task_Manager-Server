package com.example.Service;

import com.example.Model.Assignment;
import com.example.Repository.AssignmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class AssignmentService {
    private final AssignmentRepo assignmentRepo;
    @Autowired
    public AssignmentService(AssignmentRepo assignmentRepo) {
        this.assignmentRepo = assignmentRepo;
    }
    public Assignment assigntask(Assignment assignment){
        return null;
    }
}
