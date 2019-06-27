package fic.writer.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fic.writer.web.controller.request.LoginRequest;
import fic.writer.web.controller.request.SignUpRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class OauthAuthenticationTest {

    private final String OAUTH_TOKEN_URL = "/auth/login";
    private final String OAUTH_SIGNUP_URL = "/auth/signup";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void sendTokenRequest_whenSendValidEmail_ShouldReturnAccessToken() throws Exception {
        String email = "firstUser@mail.com", password = "qwerty";
        String body = serializeLoginInfoToJson(email, password);
        mockMvc.perform(post(OAUTH_TOKEN_URL)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").hasJsonPath())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void sendTokenRequest_whenSendInvalidPassword_ShouldReturnAccessToken() throws Exception {
        String email = "firstUser@mail.com", password = "invalid Password";
        String body = serializeLoginInfoToJson(email, password);
        mockMvc.perform(post(OAUTH_TOKEN_URL)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void sendTokenRequest_whenUserNotExistsAndFindByEmail() throws Exception {
        String email = "fakeUserWhoNotExists@mail.cc", password = "qwerty";
        String body = serializeLoginInfoToJson(email, password);
        mockMvc.perform(post(OAUTH_TOKEN_URL)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void sendTokenRequest_whenEmailIsNotValid_shouldReturnBadRequest() throws Exception {
        String email = "fakeUserWhoNotExists";
        String password = "qwerty";
        String body = serializeLoginInfoToJson(email, password);
        mockMvc.perform(post(OAUTH_TOKEN_URL)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void sendSignupRequest_whenEmailIsNotValid_shouldReturnBadRequest() throws Exception {
        String email = "fakeUserWhoNotExists";
        String password = "qwerty";
        String username = "name";

        String body = serializeSignupInfoToJson(email, username, password);
        mockMvc.perform(post(OAUTH_SIGNUP_URL)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void sendSignupRequest_whenEmailIsValid_shouldReturnCreated() throws Exception {
        String email = "UserWhoNotExistsYet@mail.cc";
        String password = "qwerty";
        String username = "name";

        String body = serializeSignupInfoToJson(email, username, password);
        mockMvc.perform(post(OAUTH_SIGNUP_URL)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void sendSignupRequest_whenEmailIsAlreadyUsed_shouldReturnBadRequest() throws Exception {
        String email = "firstUser@mail.com";
        String password = "qwerty";
        String username = "name";

        String body = serializeSignupInfoToJson(email, username, password);
        mockMvc.perform(post(OAUTH_SIGNUP_URL)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private String serializeLoginInfoToJson(String email, String password) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        LoginRequest loginRequest = LoginRequest.builder()
                .password(password)
                .email(email)
                .build();
        return objectMapper.writeValueAsString(loginRequest);
    }

    private String serializeSignupInfoToJson(String email, String username, String password) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        SignUpRequest loginRequest = SignUpRequest.builder()
                .password(password)
                .email(email)
                .name(username)
                .build();
        return objectMapper.writeValueAsString(loginRequest);
    }
}
