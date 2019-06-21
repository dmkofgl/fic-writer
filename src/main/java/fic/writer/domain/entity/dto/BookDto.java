package fic.writer.domain.entity.dto;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.enums.Size;
import fic.writer.domain.entity.enums.State;
import lombok.*;

import javax.validation.constraints.NotBlank;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    @NotBlank
    private String title;
    private String description;
    private Size size;
    private State state;

    public static BookDto of(Book book) {
        return builder()
                .title(book.getTitle())
                .description(book.getDescription())
                .size(book.getSize())
                .state(book.getState())
                .build();
    }
}
