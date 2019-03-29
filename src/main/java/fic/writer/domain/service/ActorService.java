package fic.writer.domain.service;

import fic.writer.domain.entity.Actor;

public interface ActorService extends CrudService<Actor, Long> {


    Actor save(Actor actor);

}
