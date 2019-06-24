package fic.writer.domain.entity;

import fic.writer.domain.entity.enums.FormattingType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Formatting {
    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;
    @NotNull
    private Long startIndex;
    @NotNull
    private Long endIndex;
    @NotNull
    @Enumerated(value = EnumType.ORDINAL)
    private FormattingType type;
}
