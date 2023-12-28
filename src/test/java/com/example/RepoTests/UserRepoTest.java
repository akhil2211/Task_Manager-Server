package com.example.RepoTests;

import com.example.Model.Organization;
import com.example.Model.RegisterRequest;
import com.example.Model.Role;
import com.example.Model.User;
import com.example.Repository.RoleRepo;
import com.example.Repository.UserRepository;
import com.example.Service.AdminService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserRepoTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepo roleRepo;
    @Test
    public void setRegTest(){
        Role role= new Role();
        role.setId(1);
        Organization organization=new Organization();
        organization.setId(1);

        User user= User.builder()
                .firstname("sdmnxz")
                .lastname("Mvsdv")
                .username("mansdvu")
                .password("Manuvs@123")
                .email("manvsvu@gmail.com")
                .role(role)
                .organization(organization)
                .build();

        userRepository.save(user);
        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }

    @Test
    public void getUserbyIdTest(){
        User user= userRepository.findById(1).get();
        Assertions.assertThat(user.getId()).isEqualTo(1);
    }
//    @Test
//    public void getUserListTest(){
//        List<User> users=userRepository.findAllUsers();
//        Assertions.assertThat(users.size()).isGreaterThan(0);
//    }

    @Test
    public void getByUserNameTest(){
        User user= userRepository.findByUsername("manu").get();
        Assertions.assertThat(user.getUsername()).isEqualTo("manu");
    }
    @Test
    public void saveDuplicateUserTest(){

        Role role= new Role();
        role.setId(2);
        Organization organization=new Organization();
        organization.setId(1);

        User user= User.builder()
                .firstname("Mayu")
                .lastname("M")
                .username("mayu")
                .password("Mayu@123")
                .email("manu@gmai.com")
                .role(role)
                .organization(organization)
                .build();
        assertThrows(DataIntegrityViolationException.class,() -> userRepository.save(user));
    }

    @Test
    public void saveUniqueUserTest(){
        Role role= new Role();
        role.setId(3);
        Organization organization=new Organization();
        organization.setId(1);

        User user= User.builder()
                .firstname("Karun")
                .lastname("M")
                .username("karun")
                .password("Karun@123")
                .email("karun@gmai.com")
                .role(role)
                .organization(organization)
                .build();
        userRepository.save(user);
        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }
    @Test
    public void existsByEmailTest() {
        boolean exists = userRepository.existsByEmail("manu@gmail.com");
        Assertions.assertThat(exists).isTrue();
    }
    @Test
    public void existsByUserNameTest() {
        boolean exists = userRepository.existsByUsername("manu");
        Assertions.assertThat(exists).isTrue();}
    @Test
    public void getbyOrgTest(){
        List<User> users=userRepository.findByOrganization(1);
        Assertions.assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    public void getUserRoleTest() {
        String role = userRepository.getUserRole(1);
        Assertions.assertThat(role).isNotNull();
    }
}


