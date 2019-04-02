package fic.writer.domain.service;

import fic.writer.domain.entity.ActorState;
import fic.writer.domain.entity.ActorStateId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ActorStateService {
    Page<ActorState> findAllByActor(Long id, Pageable pageable);

    Optional<ActorState> findForActorByArticle(Long actorId, Long articleId);

    List<ActorState> findAll();

    Page<ActorState> findPage(Pageable pageable);

    Optional<ActorState> findById(ActorStateId actorStateId);

    void delete(ActorState actorState);

    void deleteById(ActorStateId actorStateId);
}
