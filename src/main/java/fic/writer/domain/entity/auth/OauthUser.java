package fic.writer.domain.entity.auth;

import fic.writer.domain.entity.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OauthUser {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private User profile;
    private String token;
    private Date expireDate;
}
