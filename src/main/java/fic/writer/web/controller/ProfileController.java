package fic.writer.web.controller;

import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.dto.ProfileDto;
import fic.writer.domain.service.ProfileService;
import fic.writer.web.config.security.CurrentUser;
import fic.writer.web.config.security.authorization.UserPrincipal;
import fic.writer.web.controller.response.PageResponse;
import fic.writer.web.controller.response.ProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/users")
public class ProfileController {
    private static final String ID_TEMPLATE_PATH = "/{userId}";
    private static final String ID_TEMPLATE = "userId";

    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public PageResponse<ProfileResponse> getAllUsers(Pageable pageable) {
        Page<ProfileResponse> userResponses = profileService.findPage(pageable).map(ProfileResponse::new);
        return new PageResponse<>(userResponses);
    }

    @GetMapping(ID_TEMPLATE_PATH)
    public ProfileResponse getUserById(@PathVariable(ID_TEMPLATE) Long id) {
        return profileService.findById(id)
                .map(ProfileResponse::new)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse createUser(@RequestBody ProfileDto user) {
        Profile savedProfile = profileService.create(user);
        return new ProfileResponse(savedProfile);
    }

    @PutMapping(ID_TEMPLATE_PATH)
    public ProfileResponse updateUser(@PathVariable(ID_TEMPLATE) Long id, @RequestBody ProfileDto profileDto) {
        Profile savedProfile = profileService.update(id, profileDto);
        return new ProfileResponse(savedProfile);
    }

    @DeleteMapping(ID_TEMPLATE_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(Long id) {
        profileService.deleteById(id);
    }

    @RequestMapping({"/user", "/me"})
    public ProfileResponse getLoggedProfile(@CurrentUser UserPrincipal userPrincipal) {
        ProfileResponse profileResponse;

        profileResponse = profileService.findById(userPrincipal.getId())
                .map(ProfileResponse::new)
                .orElseThrow(EntityNotFoundException::new);
        return profileResponse;
    }
}
