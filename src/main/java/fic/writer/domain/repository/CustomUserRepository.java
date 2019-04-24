package fic.writer.domain.repository;

import fic.writer.domain.entity.auth.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    Optional<CustomUser> findByProfileId(Long id);
}
