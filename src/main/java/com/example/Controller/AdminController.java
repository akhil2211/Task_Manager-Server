package com.example.Controller;

import com.example.Model.RegisterRequest;
import com.example.Service.AdminService;
import com.example.Service.AuthService;
import com.example.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")

public class AdminController {
    private final AdminService adminService;

    private final AuthService authService;

    private final UserService userService;

    @Autowired
    public AdminController(AdminService adminService, AuthService authService, UserService userService) {
        this.adminService = adminService;
        this.authService = authService;
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<ResponseEntity<Object>> register (@RequestBody @Valid RegisterRequest registerRequest){
        return ResponseEntity.ok(adminService.register(registerRequest));
    }
    @PostMapping("/createOrganization")
    public ResponseEntity<ResponseEntity<Object>> createOrganziation(@RequestBody Map<String,String> orgRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createOrganization(orgRequest));
    }
    @PostMapping("/createPriority")
    public ResponseEntity<String> createPriority(@RequestBody Map<String,String> priorityRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createPriority(priorityRequest));
    }
    @PostMapping("/createCategory")
    public ResponseEntity<String> createCategory(@RequestBody Map<String, String> categoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createCategory(categoryRequest));
    }

}
