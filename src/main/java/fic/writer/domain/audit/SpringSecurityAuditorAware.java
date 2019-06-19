package fic.writer.domain.audit;

import fic.writer.domain.entity.Profile;
import fic.writer.web.config.security.authorization.UserPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<Profile> {
    @Override
    public Optional<Profile> getCurrentAuditor() {
        Optional<Profile> user = Optional.empty();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            user = Optional.ofNullable(userPrincipal.getProfileDetails().getProfile());
        }
        return user;
    }
}