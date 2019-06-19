package fic.writer.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.auth.AuthProvider;
import fic.writer.domain.entity.auth.OauthProfileDetails;
import fic.writer.domain.entity.dto.BookDto;
import fic.writer.domain.entity.enums.State;
import fic.writer.web.config.security.authorization.UserDetailsServiceImpl;
import fic.writer.web.config.security.authorization.UserPrincipal;
import fic.writer.web.config.security.oauth.TokenProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerValidationTest {
    private static final String BOOK_PATH = "/api/books";
    private static final String BOOK_ID_PATH_TEMPLATE = BOOK_PATH + "/{id}";

    @Autowired
    private BookController bookController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private TokenProvider tokenProvider;
    private String token;

    @Before
    public void setUp() throws Exception {
        String username = "username";
        String email = "email@mail.com";
        UserDetails userDetails = UserPrincipal.create(OauthProfileDetails.builder()
                .provider(AuthProvider.LOCAL)
                .profile(Profile.builder().username(username).email(email).id(1L).build())
                .id(1L)
                .build());
        Mockito.when(userDetailsService.loadUserById(anyLong())).thenReturn(userDetails);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        token = tokenProvider.createToken(authentication);
    }

    @Test
    public void checkTokenToAllowAccess() throws Exception {
        mockMvc.perform(get(BOOK_PATH).header("authorization", "bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void createBook_whenTitleIsNull_shouldReceiveBadRequest() throws Exception {
        BookDto bookDto = BookDto.builder()
                .title(null)
                .description("desc")
                .state(State.FROZEN)
                .build();
        ObjectMapper mapper = new ObjectMapper();


        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String body = mapper.writeValueAsString(bookDto);

        mockMvc.perform(post(BOOK_PATH)
                .header("authorization", "bearer " + token)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0].field").value("title"))
                .andExpect(jsonPath("$.[0].value").doesNotExist());
    }

    @Test
    public void createBook_whenTitleIsBlank_shouldReceiveBadRequest() throws Exception {
        BookDto bookDto = BookDto.builder()
                .title("")
                .description("desc")
                .state(State.FROZEN)
                .build();
        ObjectMapper mapper = new ObjectMapper();


        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String body = mapper.writeValueAsString(bookDto);

        mockMvc.perform(post(BOOK_PATH)
                .header("authorization", "bearer " + token)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0].field").value("title"))
                .andExpect(jsonPath("$.[0].value").isEmpty());
    }

    @Test
    public void createBook_whenCorrect_shouldReceiveCreated() throws Exception {
        BookDto bookDto = BookDto.builder()
                .title("new adv")
                .description("desc")
                .state(State.FROZEN)
                .build();
        ObjectMapper mapper = new ObjectMapper();


        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String body = mapper.writeValueAsString(bookDto);

        mockMvc.perform(post(BOOK_PATH)
                .header("authorization", "bearer " + token)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}
