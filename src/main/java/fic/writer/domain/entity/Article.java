package fic.writer.domain.entity;

import lombok.*;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Article {
    private static final int CHARS_IN_PAGE = 1800;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @CreatedDate
    private Date created;
    @LastModifiedDate
    private Date lastModify;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String annotation;
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
    @Formula("ceil( CHAR_LENGTH(content)/" + CHARS_IN_PAGE + ")")
    private long pageCount;

}