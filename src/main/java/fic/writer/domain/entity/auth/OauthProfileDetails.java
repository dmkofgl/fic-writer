package fic.writer.domain.entity.auth;

import fic.writer.domain.entity.Profile;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OauthProfileDetails {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @NotNull
    private Profile profile;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    private String providerId;
    private String password;
}
