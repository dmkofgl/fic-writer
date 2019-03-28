package fic.writer.domain.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
@Builder
public class Article {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private Date created;
    private String content;
    private String annotation;
    @ManyToOne
    private Book book;

}
