package fic.writer.domain.audit;

import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.auth.OauthProfileDetails;
import fic.writer.web.config.security.authorization.UserPrincipal;
import org.junit.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.Assert.*;

public class SpringSecurityAuditorAwareTest {
    SpringSecurityAuditorAware auditorAware = new SpringSecurityAuditorAware();

    @Test
    public void getCurrentAuditor_whenProfileExistInSecurityContext_shouldReturnProfile() {
        Profile profile = Profile.builder().build();
        OauthProfileDetails details = OauthProfileDetails.builder().profile(profile).build();
        UserPrincipal userPrincipal = UserPrincipal.create(details);
        Authentication authentication = new TestingAuthenticationToken(userPrincipal, details.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Optional<Profile> currentProfile = auditorAware.getCurrentAuditor();
        assertTrue(currentProfile.isPresent());
        assertEquals(profile, currentProfile.get());
    }

    @Test
    public void getCurrentAuditor_whenProfileNotExistInSecurityContext_shouldReturnEmptyOptional() {
        SecurityContextHolder.clearContext();
        Optional<Profile> currentProfile = auditorAware.getCurrentAuditor();
        assertFalse(currentProfile.isPresent());
    }
}