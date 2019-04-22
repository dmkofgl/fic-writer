package fic.writer.domain.audit;

import fic.writer.domain.entity.User;
import fic.writer.domain.service.UserService;
import fic.writer.web.config.security.authorization.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<User> {
    @Autowired
    private UserService userService;

    @Override
    public Optional<User> getCurrentAuditor() {
        Optional<User> user = Optional.ofNullable(((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());
        return user;
    }
}

