package fic.writer.domain.service;

import fic.writer.domain.entity.dto.ProfileDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfileServiceTest {
    @Autowired
    private ProfileService profileService;

    @Test
    public void createUser() {
        final String USERNAME = "createTestUser";
        ProfileDto user = ProfileDto.builder().username(USERNAME).build();
        profileService.create(user);
        assertTrue(profileService.findAll().stream().anyMatch(u -> u.getUsername().equals(USERNAME)));
    }

    @Test
    public void deleteUser() {
        final Long USER_ID = 123L;
        assertTrue(profileService.findById(USER_ID).isPresent());
        profileService.deleteById(USER_ID);
        assertFalse(profileService.findById(USER_ID).isPresent());
    }

    @Test
    public void updateUser_whenUpdateAbout_shouldChangeAbout() {
        final Long USER_ID = 1L;
        final String NEW_ABOUT = "new about";
        ProfileDto profileDto = ProfileDto.builder().about(NEW_ABOUT).build();
        profileService.update(USER_ID, profileDto);
        assertEquals(NEW_ABOUT, profileService.findById(USER_ID).get().getAbout());
    }
}