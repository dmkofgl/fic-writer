package fic.writer.domain.service.impl;

import fic.writer.domain.entity.Actor;
import fic.writer.domain.repository.ActorRepositry;
import fic.writer.domain.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActorServiceImpl implements ActorService {
    private ActorRepositry actorRepositry;

    @Autowired
    public ActorServiceImpl(ActorRepositry actorRepositry) {
        this.actorRepositry = actorRepositry;
    }


    @Override
    public List<Actor> findAll() {
        return actorRepositry.findAll();
    }

    @Override
    public Page<Actor> findPage(Pageable pageable) {
        return actorRepositry.findAll(pageable);
    }

    @Override
    public Actor findById(UUID uuid) {
        return findById(uuid);
    }
}
