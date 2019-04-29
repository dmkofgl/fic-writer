package fic.writer.domain.repository;

import fic.writer.domain.entity.auth.EmbeddedUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmbeddedUserDetailsRepository extends JpaRepository<EmbeddedUserDetails, Long> {
    Optional<EmbeddedUserDetails> findByProfileId(Long id);
}
