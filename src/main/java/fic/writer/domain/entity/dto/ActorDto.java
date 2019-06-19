package fic.writer.domain.entity.dto;

import fic.writer.domain.entity.Actor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class ActorDto {
    @NotBlank
    private String name;
    private String description;

    public static ActorDto of(Actor actor) {
        return builder()
                .name(actor.getName())
                .description(actor.getDescription())
                .build();
    }
}