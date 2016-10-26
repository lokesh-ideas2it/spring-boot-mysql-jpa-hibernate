package com.devero.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.devero.Application;
import com.devero.dao.UserDao;
import com.devero.models.User;
import com.devero.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class UserResourceTest {

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @Mock
    private UserDao userDao;

    @InjectMocks
    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.user = new User();
        user.setId(1);
        user.setEmail("test@test.com");
        user.setName("Test");
    }

    @After
    public void tearDown() {
        this.user = null;
        this.mockMvc = null;
    }

    @Test
    public void getUserByEmail() throws Exception {
        long userId = 1;
        when(userDao.getById(any(Long.class))).thenReturn(user);
        this.mockMvc.perform(get("/users/id/" + userId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Test"))).andExpect(jsonPath("$.email", equalTo("test@test.com")))
                .andReturn();
    }

    @Test
    public void createUser() throws Exception {
        doNothing().when(userDao).create(any(User.class));
        String userJson = "{ \"email\": \"test@test.com\", \"name\": \"Test\" }";
        this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.name", equalTo("Test")))
                .andExpect(jsonPath("$.email", equalTo("test@test.com"))).andReturn();
    }

    @Test
    public void updateUser() throws Exception {
        when(userDao.getById(any(Long.class))).thenReturn(user);
        when(userDao.update(any(User.class))).thenReturn(user);
        String userJson = "{ \"id\": 1, \"email\": \"test@test.com\", \"name\": \"Test\" }";
        this.mockMvc.perform(put("/users/").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Test"))).andExpect(jsonPath("$.email", equalTo("test@test.com")))
                .andReturn();
    }

    @Test
    public void deleteUser() throws Exception {
        when(userDao.getById(any(Long.class))).thenReturn(user);
        doNothing().when(userDao).delete(any(User.class));
        this.mockMvc.perform(delete("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status", equalTo("User deleted succesfully"))).andReturn();
    }
}