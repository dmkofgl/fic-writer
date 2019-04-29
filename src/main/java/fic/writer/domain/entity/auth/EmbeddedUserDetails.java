package fic.writer.domain.entity.auth;

import fic.writer.domain.entity.Profile;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmbeddedUserDetails {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    private Profile profile;
    private String password;
}
