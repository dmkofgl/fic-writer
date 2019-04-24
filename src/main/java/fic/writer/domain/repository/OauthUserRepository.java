package fic.writer.domain.repository;

import fic.writer.domain.entity.auth.OauthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthUserRepository extends JpaRepository<OauthUser, Long> {
}
