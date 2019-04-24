package fic.writer.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import fic.writer.domain.entity.User;
import fic.writer.domain.entity.dto.UserDto;
import fic.writer.domain.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {
    private static final String USERS_PATH = "/users";
    private static final String USER_ID_PATH_TEMPLATE = USERS_PATH + "/{id}";
    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;


    @Test
    public void getUsers_whenDtoIsEmpty_shouldReturnOk() throws Exception {
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
        userList.add(user);
        Mockito.when(userService.findAll()).thenReturn(userList);

        mockMvc.perform(get(USERS_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].username").value(user.getUsername()))
                .andExpect(jsonPath("$.[0].links.[0].rel").value("self"));
    }

    @Test
    public void getUserById_whenUserExists_shouldReturnOk() throws Exception {
        final long ID = 1L;
        User user = new User();
        user.setId(ID);
        user.setUsername("testUsername");

        Mockito.when(userService.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get(USER_ID_PATH_TEMPLATE, ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$._links.self").hasJsonPath());
    }

    @Test
    public void createUser() throws Exception {
        final Long USER_ID = 1L;
        final String username = "testUsername", about = "about", information = "inform";
        UserDto dto = new UserDto(username, about, information);
        ObjectMapper mapper = new ObjectMapper();

        User user = User.builder()
                .id(USER_ID)
                .username(username)
                .about(about)
                .information(information)
                .booksAsAuthor(new HashSet<>())
                .booksAsSubAuthor(new HashSet<>())
                .build();

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String body = mapper.writeValueAsString(dto);
        Mockito.when(userService.create(any(UserDto.class))).thenReturn(user);

        mockMvc.perform(post(USERS_PATH)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$._links.self").hasJsonPath());
    }

    @Test
    public void updateUser() throws Exception {
        final Long USER_ID = 1L;
        final String NEW_USERNAME = "testUsername";
        User updatedUser = User.builder()
                .id(USER_ID)
                .username(NEW_USERNAME)
                .booksAsAuthor(new HashSet<>())
                .booksAsSubAuthor(new HashSet<>())
                .build();
        UserDto dto = new UserDto(NEW_USERNAME, null, null);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String body = mapper.writeValueAsString(dto);

        Mockito.when(userService.update(anyLong(), any(UserDto.class))).thenReturn(updatedUser);

        mockMvc.perform(put(USER_ID_PATH_TEMPLATE, USER_ID)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()))
                .andExpect(jsonPath("$._links.self").hasJsonPath());

    }

    @Test
    public void deleteUser() throws Exception {
        final long ID = 1L;

        mockMvc.perform(delete(USER_ID_PATH_TEMPLATE, ID))
                .andExpect(status().isNoContent());
    }
}