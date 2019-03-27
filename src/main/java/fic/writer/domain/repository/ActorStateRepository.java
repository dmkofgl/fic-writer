package fic.writer.domain.repository;

import fic.writer.domain.entity.ActorState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActorStateRepository extends JpaRepository<ActorState, UUID> {
    Page<ActorState> findAllByActorId(UUID actorId, Pageable pageable);
}
