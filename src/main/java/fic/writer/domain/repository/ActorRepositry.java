package fic.writer.domain.repository;

import fic.writer.domain.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActorRepositry extends JpaRepository<Actor, UUID> {
}
