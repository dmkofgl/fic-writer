package fic.writer.domain.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @CreatedDate
    private Date created;
    @LastModifiedDate
    private Date lastModify;
    @Column(columnDefinition = "text")
    private String content;
    private String annotation;
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<ActorState> actorStates;
}