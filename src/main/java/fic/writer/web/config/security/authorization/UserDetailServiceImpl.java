package fic.writer.web.config.security.authorization;

import fic.writer.domain.entity.User;
import fic.writer.domain.entity.auth.CustomUser;
import fic.writer.domain.repository.CustomUserRepository;
import fic.writer.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserRepository customUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new BadCredentialsException("username: " + username + " not found"));
        CustomUser customUser = customUserRepository.findByProfileId(user.getId()).orElseThrow(() -> new BadCredentialsException("username: " + user.getEmail() + " not found"));

        CustomUserDetails customUserDetails = new CustomUserDetails(user, passwordEncoder.encode(customUser.getPassword()));

        return customUserDetails;

    }

}