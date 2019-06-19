package fic.writer.domain.repository;

import fic.writer.domain.entity.auth.OauthProfileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthProfileDetailsRepository extends JpaRepository<OauthProfileDetails, Long> {
    Optional<OauthProfileDetails> findByProfileEmail(String profile);
}