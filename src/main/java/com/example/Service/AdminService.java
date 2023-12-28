package com.example.Service;

import com.example.Model.*;
import com.example.Repository.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service

public class AdminService {

    private final OrganizationRepo organizationRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final UserRepository userRepository;
    private final PriorityRepo priorityRepo;
    private final CategoryRepo categoryRepo;
     @Autowired
    public AdminService(OrganizationRepo organizationRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo, UserRepository userRepository, PriorityRepo priorityRepo, CategoryRepo categoryRepo) {
        this.organizationRepo = organizationRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
        this.userRepository = userRepository;
        this.priorityRepo = priorityRepo;
        this.categoryRepo = categoryRepo;
    }


    public ResponseEntity<Object> register(RegisterRequest registerRequest) {
        try {

            if (userRepository.existsByEmail(registerRequest.getEmail()) && userRepository.existsByUsername(registerRequest.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Duplicate entries for both email and username. Both already exist.");
            }
            if (userRepository.existsByEmail(registerRequest.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate entry for email. This email already exists.");
            }

            if (userRepository.existsByUsername(registerRequest.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Duplicate entry for username. This username already exists.");
            }
            User user = new User();
            user.setFirstname(registerRequest.getFirstname());
            user.setLastname(registerRequest.getLastname());
            user.setUsername(registerRequest.getUsername());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setEmail(registerRequest.getEmail());
            user.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
            Role role = roleRepo.findById(Integer.valueOf(registerRequest.getRoleId())).orElse(null);
            user.setRole(role);
            Organization organization = organizationRepo.findById(Integer.valueOf(registerRequest.getOrgId())).orElse(null);
            user.setOrganization(organization);
            if(registerRequest.getReporting_officer_id()!=null){
            User reporting_officer=userRepository.findById((registerRequest.getReporting_officer_id())).orElse(null);
            if(reporting_officer!=null){
            user.setReporting_Officer(reporting_officer);}
            }

            var savedUser = userRepository.save(user);
            return ResponseEntity.ok(savedUser);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Duplicate entry found. Please check your input and try again.");
        }
    }

    public ResponseEntity<Object> createOrganization(Map<String, String> orgRequest) {
        Organization organization = new Organization();
        organization.setOrg_name(orgRequest.get("org_name"));
        organization.setOrg_code(orgRequest.get("org_code"));
        organizationRepo.save(organization);
        return ResponseEntity.status(HttpStatus.CREATED).body("Organization Added");
    }

    public String createPriority(Map<String, String> priorityRequest) {
        Priority priority = new Priority();
        priority.setType(priorityRequest.get("type"));
        priorityRepo.save(priority);
        return "Priority Created";
    }
    public String createCategory(Map<String, String> categoryRequest) {
        Category category = new Category();
        category.setC_name(categoryRequest.get("Category"));
        categoryRepo.save(category);
        return "Task Category Created !";
    }

}
