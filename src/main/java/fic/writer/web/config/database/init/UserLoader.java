package fic.writer.web.config.database.init;

import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.auth.AuthProvider;
import fic.writer.domain.entity.auth.OauthProfileDetails;
import fic.writer.domain.repository.OauthProfileDetailsRepository;
import fic.writer.domain.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserLoader implements ApplicationRunner {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private OauthProfileDetailsRepository detailsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Profile profile = Profile.builder()
                .id(1L)
                .information("first profile information")
                .username("firstUser")
                .email("firstUser@mail.com")
                .build();
        profileRepository.save(profile);
        OauthProfileDetails ownProfileDetails = OauthProfileDetails.builder()
                .id(1L)
                .password(passwordEncoder.encode("qwerty"))
                .profile(profile)
                .provider(AuthProvider.LOCAL)
                .build();
        detailsRepository.save(ownProfileDetails);


    }
}
