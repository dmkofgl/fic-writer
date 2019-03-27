package fic.writer.domain.repository;

import fic.writer.domain.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
}
