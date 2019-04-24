package fic.writer.web.controller;

import fic.writer.domain.entity.Actor;
import fic.writer.domain.entity.dto.ActorDto;
import fic.writer.domain.service.ActorService;
import fic.writer.web.response.ActorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/actors")
public class ActorController {
    private static final String ID_TEMPLATE_PATH = "/{actorId}";
    private static final String ID_TEMPLATE = "actorId";

    private ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public List<ActorResponse> getAllActors() {
        return actorService.findAll().stream()
                .map(ActorResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping(ID_TEMPLATE_PATH)
    public ActorResponse getActorById(@PathVariable(ID_TEMPLATE) Long id) {
        return actorService.findById(id)
                .map(ActorResponse::new)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    public ActorResponse createActor(ActorDto actor) {
        Actor savedActor = actorService.create(actor);
        return new ActorResponse(savedActor);
    }

    @PutMapping(ID_TEMPLATE_PATH)
    public ActorResponse updateActor(Long id, ActorDto actor) {
        Actor savedActor = actorService.update(id, actor);
        return new ActorResponse(savedActor);
    }

    @DeleteMapping(ID_TEMPLATE_PATH)
    public HttpStatus deleteActor(Long id) {
        actorService.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }
}
