package com.example.ControllerTests;

import com.example.Controller.AdminController;
import com.example.Model.Organization;
import com.example.Model.RegisterRequest;
import com.example.Model.Role;
import com.example.Model.User;
import com.example.Service.AdminService;
import com.example.Authorization.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AdminService adminService;
    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(adminController).build();
    }
    @Test
    public void testRegister() throws Exception {
        RegisterRequest request = new RegisterRequest("Mahesh", "K", "mahesh", "Mahesh@123", "mahesh@gmail.com", "1", "1");

        when(adminService.register(any())).thenReturn(ResponseEntity.accepted().body(any(User.class)));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateOrganization() throws Exception {
        Map<String, String> orgRequest = new HashMap<>();
        orgRequest.put("name", "ThinkPalm");

        when(adminService.register(any())).thenReturn(ResponseEntity.accepted().body(any(User.class)));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/createOrganization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(orgRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testCreatePriority() throws Exception {
        Map<String, String> priorityRequest = new HashMap<>();
        priorityRequest.put("name", "High");

        when(adminService.createPriority(any())).thenReturn("Priority created");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/createPriority")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(priorityRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testCreateCategory() throws Exception {
        Map<String, String> categoryRequest = new HashMap<>();
        categoryRequest.put("name", "FrontEnd");

        when(adminService.createCategory(any())).thenReturn("Category created");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/createCategory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(categoryRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
