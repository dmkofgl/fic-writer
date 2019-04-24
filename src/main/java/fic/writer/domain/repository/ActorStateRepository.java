package fic.writer.domain.repository;

import fic.writer.domain.entity.ActorState;
import fic.writer.domain.entity.ActorStateId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorStateRepository extends JpaRepository<ActorState, ActorStateId> {
    Page<ActorState> findAllByIdActorId(Long actorId, Pageable pageable);

    Optional<ActorState> findAByIdActorIdAndIdArticleId(Long actorId, Long articleId);
}
