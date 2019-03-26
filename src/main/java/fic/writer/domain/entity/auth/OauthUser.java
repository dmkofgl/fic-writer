package fic.writer.domain.entity.auth;

import fic.writer.domain.entity.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.UUID;

@Entity
public class OauthUser {
    @Id
    @GeneratedValue
    private UUID uuid;
    @OneToOne
    private User profile;
    private String token;
    private Date expireDate;
}
