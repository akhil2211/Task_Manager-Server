package com.example.ServiceTests;

import com.example.Model.Organization;
import com.example.Model.Role;
import com.example.Model.User;
import com.example.Repository.UserRepository;
import com.example.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testGetAllUsers() {
//        Role role= new Role();
//        role.setId(2);
//        Organization organization=new Organization();
//        organization.setId(1);
//
//        User user1= User.builder()
//                .firstname("Karun")
//                .lastname("M")
//                .username("karun")
//                .password("Karun@123")
//                .email("karun@gmail.com")
//                .role(role)
//                .organization(organization)
//                .build();
//        User user2= User.builder()
//                .firstname("Akhil")
//                .lastname("Nair")
//                .username("akhil")
//                .password("Akhil@123")
//                .email("akhil@gmail.com")
//                .role(role)
//                .organization(organization)
//                .build();
//
//        List<User> userList = Arrays.asList(user1, user2);
//
//        when(userRepository.findAllUsers()).thenReturn(userList);
//
//
//        List<User> result = userService.getAllUsers();
//
//        assertEquals(2, result.size());
//        assertEquals("karun", result.get(0).getUsername());
//        assertEquals("akhil", result.get(1).getUsername());
//        verify(userRepository, times(1)).findAllUsers();
//    }

    @Test
    public void testGetUserById() {
        Role role= new Role();
        role.setId(2);
        Organization organization=new Organization();
        organization.setId(1);
        User user= User.builder()
                .firstname("Akhil")
                .lastname("Nair")
                .username("akhil")
                .password("Akhil@123")
                .email("akhil@gmail.com")
                .role(role)
                .organization(organization)
                .build();

        when(userRepository.getUserById(1)).thenReturn(user);


        User result = userService.getUserById(1);


        assertEquals("akhil", result.getUsername());
        verify(userRepository, times(1)).getUserById(1);
    }

    @Test
    public void testGetUserByOrganization() {
        Role role= new Role();
        role.setId(2);
        Organization organization=new Organization();
        organization.setId(1);

        User user1= User.builder()
                .firstname("Karun")
                .lastname("M")
                .username("karun")
                .password("Karun@123")
                .email("karun@gmail.com")
                .role(role)
                .organization(organization)
                .build();
        User user2= User.builder()
                .firstname("Akhil")
                .lastname("Nair")
                .username("akhil")
                .password("Akhil@123")
                .email("akhil@gmail.com")
                .role(role)
                .organization(organization)
                .build();
        List<User> userList = Arrays.asList(user1, user2);

        when(userRepository.findByOrganization(1)).thenReturn(userList);

        List<User> result = userService.getUserByOrganization(1);


        assertEquals(2, result.size());
        assertEquals("karun", result.get(0).getUsername());
        assertEquals("akhil", result.get(1).getUsername());
        verify(userRepository, times(1)).findByOrganization(1);
    }

    @Test
    public void testGetUserDetails() {
        Role role= new Role();
        role.setId(2);
        Organization organization=new Organization();
        organization.setId(1);
        User user= User.builder()
                .firstname("Akhil")
                .lastname("Nair")
                .username("akhil")
                .password("Akhil@123")
                .email("akhil@gmail.com")
                .role(role)
                .organization(organization)
                .build();

        when(userRepository.getUserById(1)).thenReturn(user);

        User result = userService.getUserDetails(1);

        assertEquals("akhil", result.getUsername());
        assertEquals("", result.getPassword());
        verify(userRepository, times(1)).getUserById(1);
    }
}
