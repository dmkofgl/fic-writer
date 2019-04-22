package fic.writer.web.controller;

import fic.writer.domain.entity.User;
import fic.writer.domain.entity.dto.UserDto;
import fic.writer.domain.service.UserService;
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

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping(ID_TEMPLATE_PATH)
    public UserResponse getUserById(@PathVariable(ID_TEMPLATE) Long id) {
        return userService.findById(id)
                .map(UserResponse::new)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UserDto user) {
        User savedUser = userService.create(user);
        return new UserResponse(savedUser);
    }

    @PutMapping(ID_TEMPLATE_PATH)
    public UserResponse updateUser(@PathVariable(ID_TEMPLATE) Long id, @RequestBody UserDto userDto) {
        User savedUser = userService.update(id, userDto);
        return new UserResponse(savedUser);
    }

    @DeleteMapping(ID_TEMPLATE_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(Long id) {
        userService.deleteById(id);
    }

    @RequestMapping({"/user", "/me"})
    public UserResponse user(Principal principal) {
        UserResponse user = userService.findByEmail(principal.getName()).map(UserResponse::new).orElseThrow(EntityNotFoundException::new);
        return user;
    }
}
