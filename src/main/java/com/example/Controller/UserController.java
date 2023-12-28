package com.example.Controller;

import com.example.CustomContextHolder.AppContextHolder;
import com.example.Model.User;
import com.example.Repository.UserRepository;
import com.example.Service.AdminService;
import com.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")

public class UserController {
    private final UserService userService;
    private final AdminService adminService;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    public UserController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping("/profile/{user_id}")
    public ResponseEntity<User> getUserDetails(@PathVariable Integer user_id){
        return ResponseEntity.ok(userService.getUserDetails(user_id));
    }
    @GetMapping("/currentUserProfile")
    public ResponseEntity<Optional<User>> getCurrentUser() {
        Integer currentUserId = AppContextHolder.getUserId();
        Optional<User> userDetails=userRepository.findById(currentUserId);
        userDetails.get().setPassword("");
        return ResponseEntity.status(HttpStatus.OK).body(userDetails);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer user_id) {
        User user = userService.getUserById(user_id);
        if (user != null)
        {
            return ResponseEntity.ok(user);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/list")
    public ResponseEntity <List<Map<String,Object>>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/search")
    public ResponseEntity <List<Map<String,Object>>> searchByName(@RequestParam("value") String name) {
        return ResponseEntity.ok(userService.searchUser(name));
    }

    @GetMapping("/organization/{organization_id}")
    public ResponseEntity<List<User>> findByOrganization(@PathVariable Integer organization_id) {
        return new ResponseEntity<>(userService.getUserByOrganization(organization_id), HttpStatus.OK);
    }
    @GetMapping("/team")
    public ResponseEntity<List<User>> findByReportingOfficer( ){
        return new ResponseEntity<>(userService.getUserByReportingOfficer(),HttpStatus.OK);
    }


}
