package fic.writer.domain.service.impl;

import fic.writer.domain.entity.Genre;
import fic.writer.domain.repository.GenreRepository;
import fic.writer.domain.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GenreServiceImpl implements GenreService {
    private GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }


    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Page<Genre> findPage(Pageable pageable) {
        return genreRepository.findAll(pageable);
    }

    @Override
    public Optional<Genre> findById(UUID uuid) {
        return genreRepository.findById(uuid);
    }
}
