package fic.writer.domain.service.impl;

import fic.writer.domain.entity.ActorState;
import fic.writer.domain.repository.ActorStateRepository;
import fic.writer.domain.service.ActorStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ActorStateServiceImpl implements ActorStateService {
    private ActorStateRepository actorStateRepository;

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
    public Optional<ActorState> findById(UUID uuid) {
        return actorStateRepository.findById(uuid);
    }

    @Override
    public ActorState save(ActorState actorState) {
        return actorStateRepository.save(actorState);
    }
}
