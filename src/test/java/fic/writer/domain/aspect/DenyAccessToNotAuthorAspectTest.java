package fic.writer.domain.aspect;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.auth.AuthProvider;
import fic.writer.domain.entity.auth.OauthProfileDetails;
import fic.writer.domain.repository.BookRepository;
import fic.writer.domain.service.BookService;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class DenyAccessToNotAuthorAspectTest {
    private static final String BOOK_PATH = "/api/books";
    private static final String BOOK_ID_PATH_TEMPLATE = BOOK_PATH + "/{id}";
    private static final String ARTICLES_PATH_TEMPLATE = BOOK_ID_PATH_TEMPLATE + "/articles";
    private static final String ARTICLES_ID_PATH_TEMPLATE = BOOK_ID_PATH_TEMPLATE + "/articles/{articleId}";


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private BookRepository bookRepository;
    @Autowired
    private TokenProvider tokenProvider;
    private String token;
    @Autowired
    private BookService bookService;

    @Before
    public void setUp() throws Exception {
        UserDetails userDetails = UserPrincipal.create(OauthProfileDetails.builder()
                .provider(AuthProvider.LOCAL)
                .profile(createProfileWithId(1L))
                .id(1L)
                .build());
        Mockito.when(userDetailsService.loadUserById(anyLong())).thenReturn(userDetails);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        token = tokenProvider.createToken(authentication);
    }

    @Test
    public void beforeDeleteBook_whenDifferentProfiles_shouldReturnForbidden() throws Exception {
        Long bookId = 1L;
        Profile author = createProfileWithId(2L);
        Book book = Book.builder().author(author).build();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        mockMvc.perform(delete(BOOK_ID_PATH_TEMPLATE, bookId)
                .header("authorization", "bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void beforeDeleteBook_whenProfileIsAuthor_shouldReturnNoContent() throws Exception {
        Long bookId = 1L;
        Profile author = createProfileWithId(1L);
        Book book = Book.builder().author(author).build();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        mockMvc.perform(delete(BOOK_ID_PATH_TEMPLATE, bookId)
                .header("authorization", "bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void beforeDeleteBook_whenProfileIsCoauthor_shouldReturnNoContent() throws Exception {
        Long bookId = 1L;
        Profile author = createProfileWithId(2L);
        Profile coauthor = createProfileWithId(1L);
        Book book = Book.builder().author(author).coauthors(coauthor).build();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        mockMvc.perform(delete(BOOK_ID_PATH_TEMPLATE, bookId)
                .header("authorization", "bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void beforeAddArticle_whenDifferentProfiles_shouldReturnForbidden() throws Exception {
        Long bookId = 1L;
        Profile author = createProfileWithId(2L);
        Book book = Book.builder().author(author).build();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        mockMvc.perform(post(ARTICLES_PATH_TEMPLATE, bookId)
                .header("authorization", "bearer " + token)
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void beforeAddArticle_whenProfileIsAuthor_shouldReturnCreated() throws Exception {
        Long bookId = 1L;
        Profile author = createProfileWithId(1L);
        Book book = Book.builder().author(author).build();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.getOne(bookId)).thenReturn(book);

        mockMvc.perform(post(ARTICLES_PATH_TEMPLATE, bookId)
                .header("authorization", "bearer " + token)
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void beforeAddArticle_whenProfileIsCoauthor_shouldReturnCreated() throws Exception {
        Long bookId = 1L;
        Profile author = createProfileWithId(2L);
        Profile coauthor = createProfileWithId(1L);
        Book book = Book.builder().author(author).coauthors(coauthor).build();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.getOne(bookId)).thenReturn(book);

        mockMvc.perform(post(ARTICLES_PATH_TEMPLATE, bookId)
                .header("authorization", "bearer " + token)
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void beforeRemoveArticle_whenDifferentProfiles_shouldReturnForbidden() throws Exception {
        Long bookId = 1L;
        Long articleId = 1L;
        Profile author = createProfileWithId(2L);
        Book book = Book.builder().author(author).build();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        mockMvc.perform(delete(ARTICLES_ID_PATH_TEMPLATE, bookId, articleId)
                .header("authorization", "bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void beforeRemoveArticle_whenProfileIsAuthor_shouldReturnNoContent() throws Exception {
        Long bookId = 1L;
        Long articleId = 1L;
        Profile author = createProfileWithId(2L);
        Profile coauthor = createProfileWithId(1L);
        Book book = Book.builder().author(author).coauthors(coauthor).build();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.getOne(bookId)).thenReturn(book);

        mockMvc.perform(delete(ARTICLES_ID_PATH_TEMPLATE, bookId, articleId)
                .header("authorization", "bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private Profile createProfileWithId(Long id) {
        String username = "username";
        String email = "email@mail.com";
        return Profile.builder().username(username).email(email).id(id).build();
    }
}