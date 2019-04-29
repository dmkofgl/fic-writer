package fic.writer.web.config.database.init;

import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.auth.EmbeddedUserDetails;
import fic.writer.domain.repository.EmbeddedUserDetailsRepository;
import fic.writer.domain.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class UserLoader implements ApplicationRunner {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    EmbeddedUserDetailsRepository embeddedUserDetailsRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Profile profile = Profile.builder()
                .id(1L)
                .information("first profile information")
                .username("profile@mail.cc")
                .email("firstUser@mail.com")
                .build();
        profileRepository.save(profile);
        EmbeddedUserDetails embeddedUserDetails = EmbeddedUserDetails.builder()
                .id(1L)
                .password("qwerty")
                .profile(profile)
                .build();
        embeddedUserDetailsRepository.save(embeddedUserDetails);


    }
}
