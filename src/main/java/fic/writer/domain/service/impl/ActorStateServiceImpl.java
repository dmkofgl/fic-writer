package fic.writer.domain.service.impl;

import fic.writer.domain.entity.ActorState;
import fic.writer.domain.entity.ActorStateId;
import fic.writer.domain.entity.dto.ActorStateDto;
import fic.writer.domain.repository.ActorRepository;
import fic.writer.domain.repository.ActorStateRepository;
import fic.writer.domain.service.ActorStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorStateServiceImpl implements ActorStateService {
    private ActorStateRepository actorStateRepository;
    private ActorRepository actorRepository;

    @Autowired
    public ActorStateServiceImpl(ActorStateRepository actorStateRepository) {
        this.actorStateRepository = actorStateRepository;
    }

    @Override
    public List<ActorState> findAll() {
        return actorStateRepository.findAll();
    }


    @Override
    public Page<ActorState> findPage(Pageable pageable) {
        return actorStateRepository.findAll(pageable);
    }

    @Override
    public Optional<ActorState> findById(ActorStateId id) {
        return actorStateRepository.findById(id);
    }

    @Override
    public void delete(ActorState actorState) {
        actorStateRepository.delete(actorState);
    }

    @Override
    public void deleteById(ActorStateId id) {
        actorStateRepository.deleteById(id);
    }

    @Override
    public Page<ActorState> findAllByActor(Long id, Pageable pageable) {
        return actorStateRepository.findAllByIdActorId(id, pageable);
    }

    @Override
    public Optional<ActorState> findForActorByArticle(Long actorId, Long articleId) {
        return actorStateRepository.findAByIdActorIdAndIdArticleId(actorId, articleId);
    }

    private ActorState actorStateDtoForActorState(ActorStateDto actorState) {
        return ActorState.builder()
                .id(actorState.getId())
                .title(actorState.getTitle())
                .content(actorState.getContent())
                .build();
    }
}
