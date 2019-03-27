package fic.writer.domain.service;

import fic.writer.domain.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenreService {
    List<Genre> findAll();

    Page<Genre> findPage(Pageable pageable);

    Optional<Genre> findById(UUID uuid);
}
