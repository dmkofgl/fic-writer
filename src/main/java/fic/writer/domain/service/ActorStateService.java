package fic.writer.domain.service;

import fic.writer.domain.entity.ActorState;
import fic.writer.domain.entity.dto.ActorStateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ActorStateService extends CrudService<ActorState, Long> {

    Page<ActorState> findAllByActor(Long id, Pageable pageable);

    Optional<ActorState> findForActorByArticle(Long actorId, Long articleId);

    ActorState save(ActorStateDto actorStateDto);
}
