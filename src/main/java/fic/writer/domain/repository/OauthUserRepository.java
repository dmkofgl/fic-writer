package fic.writer.domain.repository;

import fic.writer.domain.entity.auth.OauthUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthUserRepository extends JpaRepository<OauthUserDetails, Long> {
}
