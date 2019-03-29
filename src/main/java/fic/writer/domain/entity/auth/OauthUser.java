package fic.writer.domain.entity.auth;

import fic.writer.domain.entity.User;
import lombok.*;

import javax.persistence.*;
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
    @OneToOne(fetch = FetchType.EAGER)
    private User profile;
    private String token;
    private Date expireDate;
}
