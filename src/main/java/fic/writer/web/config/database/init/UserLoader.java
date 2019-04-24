package fic.writer.web.config.database.init;

import fic.writer.domain.entity.User;
import fic.writer.domain.entity.auth.CustomUser;
import fic.writer.domain.repository.CustomUserRepository;
import fic.writer.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class UserLoader implements ApplicationRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomUserRepository customUserRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = User.builder()
                .id(1L)
                .information("first user information")
                .username("user@mail.cc")
                .email("firstUser@mail.com")
                .build();
        userRepository.save(user);
        CustomUser customUser = CustomUser.builder()
                .id(1L)
                .password("qwerty")
                .profile(user)
                .build();
        customUserRepository.save(customUser);


    }
}
