package fic.writer.domain.entity.auth;

import fic.writer.domain.entity.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
public class CustomUser {
    @Id
    @GeneratedValue
    private UUID uuid;
    @OneToOne
    private User profile;
    private String email;
    private String password;
}
