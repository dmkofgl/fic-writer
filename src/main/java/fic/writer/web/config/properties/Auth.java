package fic.writer.web.config.properties;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Auth {
    private String tokenSecret;
    private long tokenExpirationMsec;

}
