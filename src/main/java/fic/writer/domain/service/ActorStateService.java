package fic.writer.domain.service;

import fic.writer.domain.entity.ActorState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActorStateService {
    List<ActorState> findAll();

    Page<ActorState> findPage(Pageable pageable);

    Optional<ActorState> findById(UUID uuid);

    ActorState save(ActorState actorState);
}
