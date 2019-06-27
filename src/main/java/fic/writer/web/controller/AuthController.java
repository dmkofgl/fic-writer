package fic.writer.web.controller;

import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.auth.AuthProvider;
import fic.writer.domain.entity.auth.OauthProfileDetails;
import fic.writer.domain.service.OauthProfileDetailsService;
import fic.writer.domain.service.ProfileService;
import fic.writer.domain.utils.MapConstructor;
import fic.writer.exception.BadRequestException;
import fic.writer.web.config.security.oauth.TokenProvider;
import fic.writer.web.controller.request.LoginRequest;
import fic.writer.web.controller.request.SignUpRequest;
import fic.writer.web.controller.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private OauthProfileDetailsService profileDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (profileService.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Email address already in use.");
        }

        Profile user = Profile.builder()
                .username(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .build();
        OauthProfileDetails details = OauthProfileDetails.builder()
                .provider(AuthProvider.LOCAL)
                .profile(user)
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();

        OauthProfileDetails result = profileDetailsService.save(details);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getProfile().getId()).toUri();

        Map<String, Object> map = MapConstructor.<String, Object>getNew()
                .put("success", true)
                .put("message", "User registered successfully")
                .getMap();
        return ResponseEntity.created(location)
                .body(map);
    }

}
