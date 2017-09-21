package tests.com.bookstore.controller;

import com.bookstore.Application;
import com.bookstore.controller.UserController;
import com.bookstore.model.Role;
import com.bookstore.model.User;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by krzysztof.mazur on 21.09.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@WebAppConfiguration
@EnableWebMvc
public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @Rollback
    @WithMockUser(roles="ADMIN")
    public void user() throws Exception {
        Role roleUser = roleRepository.findOne(Role.RoleName.ROLE_MEMBER.getId());
        User first = new User("name1", "surname1", "login1", "pass", "email1@email.com", true, roleUser);
        first.setId(1L);

        when(userRepository.findById(1L)).thenReturn(first);

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("name1")))
                .andExpect(jsonPath("surname", is("surname1")))
                .andExpect(jsonPath("login", is("login1")))
                .andExpect(jsonPath("password", is("pass")))
                .andExpect(jsonPath("email", is("email1@email.com")))
                .andExpect(jsonPath("enabled", is(true)));
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void getUsers() throws Exception {

        Role roleUser = roleRepository.findOne(Role.RoleName.ROLE_MEMBER.getId());

        User first = new User("name1", "surname1", "login1", "pass", "email1@email.com", true, roleUser);
        User second = new User("name2", "surname2", "login2", "pass2", "email2@email.com", true, roleUser);

        when(userRepository.findAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void add() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

}