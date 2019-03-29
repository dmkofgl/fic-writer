package fic.writer.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private Date created;
    private String content;
    private String annotation;
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

}
