package fic.writer.domain.service;

import fic.writer.domain.entity.Actor;
import fic.writer.domain.entity.dto.ActorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ActorService {
    Actor getOne(Long id);

    Actor create(ActorDto actor);

    List<Actor> findAll();

    Page<Actor> findPage(Pageable pageable);

    Optional<Actor> findById(Long id);

    Actor update(Long id, ActorDto actor);

    void deleteById(Long aLong);
}
