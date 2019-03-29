package fic.writer.domain.entity.auth;

import fic.writer.domain.entity.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomUser {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private User profile;
    private String email;
    private String password;
}
