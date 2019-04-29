package fic.writer.domain.service.impl;

import fic.writer.domain.entity.Actor;
import fic.writer.domain.entity.dto.ActorDto;
import fic.writer.domain.repository.ActorRepository;
import fic.writer.domain.service.ActorService;
import fic.writer.domain.service.helper.ActorFlusher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {
    private ActorRepository actorRepository;

    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public List<Actor> findAll() {
        return actorRepository.findAll();
    }

    @Override
    public Page<Actor> findPage(Pageable pageable) {
        return actorRepository.findAll(pageable);
    }

    @Override
    public Optional<Actor> findById(Long id) {
        return actorRepository.findById(id);
    }

    @Override
    public Actor getOne(Long id) {
        return actorRepository.getOne(id);
    }

    @Override
    public Actor create(ActorDto actorDto) {
        Actor actor = Actor.builder().build();
        ActorFlusher.flushArticleDtoToArticle(actor, actorDto);
        return actorRepository.save(actor);
    }

    @Override
    public Actor update(Long id, ActorDto actorDto) {
        Actor actor = actorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        ActorFlusher.flushArticleDtoToArticle(actor, actorDto);
        return actorRepository.save(actor);
    }

    @Override
    public void deleteById(Long id) {
        actorRepository.deleteById(id);
    }

}
