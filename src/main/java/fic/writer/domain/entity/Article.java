package fic.writer.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.UUID;

@Entity
public class Article {
    @Id
    private Long id;
    private String title;
    private Date created;
    private String content;
    private String annotation;
    @ManyToOne
    private Book book;

}
