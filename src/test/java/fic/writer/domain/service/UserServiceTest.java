package fic.writer.domain.service;

import fic.writer.domain.entity.dto.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
        final String USERNAME = "createTestUser";
        UserDto user = UserDto.builder().username(USERNAME).build();
        userService.create(user);
        assertTrue(userService.findAll().stream().anyMatch(u -> u.getUsername().equals(USERNAME)));
    }

    @Test
    public void deleteUser() {
        final Long USER_ID = 123L;
        assertTrue(userService.findById(USER_ID).isPresent());
        userService.deleteById(USER_ID);
        assertFalse(userService.findById(USER_ID).isPresent());
    }

    @Test
    public void updateUser_whenUpdateAbout_shouldChangeAbout() {
        final Long USER_ID = 1L;
        final String NEW_ABOUT = "new about";
        UserDto userDto = UserDto.builder().about(NEW_ABOUT).build();
        userService.update(USER_ID, userDto);
        assertEquals(NEW_ABOUT, userService.findById(USER_ID).get().getAbout());
    }
}