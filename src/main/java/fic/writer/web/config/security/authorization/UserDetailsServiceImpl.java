package fic.writer.web.config.security.authorization;

import fic.writer.domain.entity.auth.OauthProfileDetails;
import fic.writer.domain.repository.OauthProfileDetailsRepository;
import fic.writer.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private OauthProfileDetailsRepository profileDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        OauthProfileDetails profileDetails = profileDetailsRepository
                .findByProfileEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        return UserPrincipal.create(profileDetails);
    }

    public UserDetails loadUserById(Long id) {
        OauthProfileDetails profileDetails = profileDetailsRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("profile", "id", id));

        return UserPrincipal.create(profileDetails);
    }
}