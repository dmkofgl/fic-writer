package fic.writer.domain.entity.dto;

import fic.writer.domain.entity.enums.Size;
import fic.writer.domain.entity.enums.State;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String title;
    private String description;
    private Size size;
    private State state;
}
