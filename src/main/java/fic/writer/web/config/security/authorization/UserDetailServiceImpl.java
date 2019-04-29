package fic.writer.web.config.security.authorization;

import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.auth.EmbeddedUserDetails;
import fic.writer.domain.repository.EmbeddedUserDetailsRepository;
import fic.writer.domain.repository.ProfileRepository;
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
    private ProfileRepository profileRepository;
    private EmbeddedUserDetailsRepository embeddedUserDetailsRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailServiceImpl(ProfileRepository profileRepository, EmbeddedUserDetailsRepository embeddedUserDetailsRepository, PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
        this.embeddedUserDetailsRepository = embeddedUserDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile profile = profileRepository.findByEmail(username).orElseThrow(() -> new BadCredentialsException("username: " + username + " not found"));
        EmbeddedUserDetails embeddedUserDetails = embeddedUserDetailsRepository.findByProfileId(profile.getId()).orElseThrow(() -> new BadCredentialsException("username: " + profile.getEmail() + " not found"));

        return new EmbeddedProfileDetails(profile, passwordEncoder.encode(embeddedUserDetails.getPassword()));
    }

}