package fic.writer.web.config.security.authorization;

import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.auth.OauthProfileDetails;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserPrincipalTest {

    @Test
    public void createUserPrincipal_withProfileId_shouldEqualsProfileIdAndUserPrincipalId() {
        final Long profileId = 1L;
        Profile profile = Profile.builder()
                .id(profileId).build();
        OauthProfileDetails details = OauthProfileDetails.builder().profile(profile).build();
        UserPrincipal userPrincipal = UserPrincipal.create(details);

        assertEquals(profileId, userPrincipal.getId());
    }

    @Test
    public void createUserPrincipal_WithProfileEmail_shouldEqualsProfileEmailAndPrincipalUsername() {
        final String email = "email@mail.com";
        Profile profile = Profile.builder().email(email).build();
        OauthProfileDetails details = OauthProfileDetails.builder().profile(profile).build();

        UserPrincipal userPrincipal = UserPrincipal.create(details);

        assertEquals(email, userPrincipal.getUsername());
    }

    @Test
    public void createUserPrincipal_WithProfileDetails_shouldEqualsProfileDetails() {
        Profile profile = Profile.builder().build();
        OauthProfileDetails details = OauthProfileDetails.builder().profile(profile).build();

        UserPrincipal userPrincipal = UserPrincipal.create(details);

        assertEquals(details, userPrincipal.getProfileDetails());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUserPrincipal_withoutProfile_shouldThrowException() {
        OauthProfileDetails details = OauthProfileDetails.builder().build();

        UserPrincipal.create(details);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUserPrincipal_withoutProfileDetails_shouldThrowException() {
        UserPrincipal.create(null);
    }

}