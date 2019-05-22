package fic.writer.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.dto.ProfileDto;
import fic.writer.domain.service.ProfileService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProfileController.class, secure = false)
public class ProfileControllerTest {
    private static final String USERS_PATH = "/api/users";
    private static final String USER_ID_PATH_TEMPLATE = USERS_PATH + "/{id}";
    @Autowired
    private ProfileController profileController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProfileService profileService;


    @Test
    public void getUsers_whenDtoIsEmpty_shouldReturnOk() throws Exception {
        final long ID = 1L;
        final String username = "testUsername";

        List<Profile> profileList = Lists.list();
        Profile profile = Profile.builder()
                .id(ID)
                .username(username)
                .build();
        profileList.add(profile);
        Mockito.when(profileService.findPage(any(Pageable.class))).thenReturn(new PageImpl<>(profileList));

        mockMvc.perform(get(USERS_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.profileResponseList.[0].username").value(profile.getUsername()))
                .andExpect(jsonPath("$._embedded.profileResponseList.[0]._links.self").hasJsonPath());
    }

    @Test
    public void getUserById_whenUserExists_shouldReturnOk() throws Exception {
        final long ID = 1L;
        final String username = "testUsername";
        Profile profile = Profile.builder()
                .id(ID)
                .username(username)
                .build();

        Mockito.when(profileService.findById(1L)).thenReturn(Optional.of(profile));

        mockMvc.perform(get(USER_ID_PATH_TEMPLATE, ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$._links.self").hasJsonPath());
    }

    @Test
    public void createUser() throws Exception {
        final Long USER_ID = 1L;
        final String username = "testUsername",
                about = "about",
                information = "inform";
        ProfileDto profileDto = ProfileDto.builder()
                .username(username)
                .about(about)
                .information(information)
                .build();
        ObjectMapper mapper = new ObjectMapper();

        Profile profile = Profile.builder()
                .id(USER_ID)
                .username(username)
                .about(about)
                .information(information)
                .build();

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String body = mapper.writeValueAsString(profileDto);
        Mockito.when(profileService.create(any(ProfileDto.class))).thenReturn(profile);

        mockMvc.perform(post(USERS_PATH)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(profile.getUsername()))
                .andExpect(jsonPath("$._links.self").hasJsonPath());
    }

    @Test
    public void updateUser() throws Exception {
        final Long USER_ID = 1L;
        final String NEW_USERNAME = "testUsername";
        Profile updatedProfile = Profile.builder()
                .id(USER_ID)
                .username(NEW_USERNAME)
                .booksAsAuthor(new HashSet<>())
                .booksAsCoauthor(new HashSet<>())
                .build();
        ProfileDto dto = ProfileDto.builder()
                .username(NEW_USERNAME)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String body = mapper.writeValueAsString(dto);

        Mockito.when(profileService.update(anyLong(), any(ProfileDto.class))).thenReturn(updatedProfile);

        mockMvc.perform(put(USER_ID_PATH_TEMPLATE, USER_ID)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(updatedProfile.getUsername()))
                .andExpect(jsonPath("$._links.self").hasJsonPath());

    }

    @Test
    public void deleteUser() throws Exception {
        final long ID = 1L;

        mockMvc.perform(delete(USER_ID_PATH_TEMPLATE, ID))
                .andExpect(status().isNoContent());
    }
}