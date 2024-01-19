package com.example.Service;


import com.example.CustomContextHolder.AppContextHolder;
import com.example.Model.User;
import com.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
    public List<Map<String,Object>> getAllUsers(){
       return userRepo.findAllUsers();
    }

    public User getUserById(Integer user_id) {

        return userRepo.getUserById(user_id);
    }

    public List<User> getUserByOrganization(Integer org_id) {
        return userRepo.findByOrganization(org_id);
    }

    public User getUserDetails(Integer userId) {
        User user= userRepo.getUserById(userId);
        user.setPassword("");
        return user;

    }
    public List<Map<String,Object>> getUserByReportingOfficer() {
        Integer currentUserId = AppContextHolder.getUserId();
        Optional<User> userDetails=userRepo.findById(currentUserId);
        return userRepo.findByReportingOfficer(userDetails.get().getId());
    }

    public List<Map<String, Object>> searchUser(String value) {
        return userRepo.searchUserByName(value);
    }

    public Map<String,Object> getUserProfile() {
        Integer currentUserId = AppContextHolder.getUserId();
        return userRepo.findUserProfile(currentUserId);
    }

    public List<User> getReportingOfficerList(Integer roleId) {

        return userRepo.findReportingOfficerList(roleId);
    }

    public  List<Map<String,Object>> getProjectsByUser() {
        Integer currentUserId = AppContextHolder.getUserId();
        return userRepo.findUserProjects(currentUserId);
    }
}
