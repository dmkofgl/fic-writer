package fic.writer.domain.audit;

import fic.writer.domain.entity.Profile;
import fic.writer.domain.service.ProfileService;
import fic.writer.web.config.security.authorization.EmbeddedProfileDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<Profile> {
    @Autowired
    private ProfileService profileService;

    @Override
    public Optional<Profile> getCurrentAuditor() {
        Optional<Profile> user = Optional.empty();
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            user = Optional.ofNullable(((EmbeddedProfileDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getProfile());
        }

        return user;
    }
}