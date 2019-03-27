package fic.writer.domain.service;

import fic.writer.domain.entity.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ActorService {
    List<Actor> findAll();

    Page<Actor> findPage(Pageable pageable);

    Actor findById(UUID uuid);

}
