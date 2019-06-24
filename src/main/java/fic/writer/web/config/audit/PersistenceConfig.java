package fic.writer.web.config.audit;

import fic.writer.domain.audit.SpringSecurityAuditorAware;
import fic.writer.domain.entity.Profile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {

    @Bean
    public AuditorAware<Profile> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }
}
