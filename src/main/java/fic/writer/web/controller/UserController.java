package fic.writer.web.controller;

import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.dto.ProfileDto;
import fic.writer.domain.service.ProfileService;
import fic.writer.web.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final String ID_TEMPLATE_PATH = "/{userId}";
    private static final String ID_TEMPLATE = "userId";

    private ProfileService profileService;

    @Autowired
    public UserController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return profileService.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping(ID_TEMPLATE_PATH)
    public UserResponse getUserById(@PathVariable(ID_TEMPLATE) Long id) {
        return profileService.findById(id)
                .map(UserResponse::new)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody ProfileDto user) {
        Profile savedProfile = profileService.create(user);
        return new UserResponse(savedProfile);
    }

    @PutMapping(ID_TEMPLATE_PATH)
    public UserResponse updateUser(@PathVariable(ID_TEMPLATE) Long id, @RequestBody ProfileDto profileDto) {
        Profile savedProfile = profileService.update(id, profileDto);
        return new UserResponse(savedProfile);
    }

    @DeleteMapping(ID_TEMPLATE_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(Long id) {
        profileService.deleteById(id);
    }

    @RequestMapping({"/user", "/me"})
    public UserResponse getLoggedProfile(Principal principal) {
        String username = principal.getName();
        return profileService.findByEmail(username)
                .map(UserResponse::new)
                .orElseThrow(EntityNotFoundException::new);
    }
}
