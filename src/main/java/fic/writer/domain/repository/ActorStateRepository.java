package fic.writer.domain.repository;

import fic.writer.domain.entity.ActorState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorStateRepository extends JpaRepository<ActorState, Long> {
    Page<ActorState> findAllByActorId(Long actorId, Pageable pageable);

    Optional<ActorState> findAByActorIdAndArticleId(Long actorId, Long articleId);
}
