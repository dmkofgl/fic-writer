package fic.writer.web.config.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public final class OAuth2 {
    private List<String> authorizedRedirectUris = new ArrayList<>();
}
