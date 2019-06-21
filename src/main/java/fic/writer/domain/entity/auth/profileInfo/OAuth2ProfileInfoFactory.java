package fic.writer.domain.entity.auth.profileInfo;

import fic.writer.domain.entity.auth.AuthProvider;
import fic.writer.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2ProfileInfoFactory {

    public static OAuth2ProfileInfo getOAuth2ProfileInfo(AuthProvider authProvider, Map<String, Object> attributes) {

        switch (authProvider) {
            case GOOGLE:
                return new GoogleOAuth2ProfileInfo(attributes);
            case GITHUB:
                return new GithubOAuth2ProfileInfo(attributes);
            default:
                throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + authProvider.name().toLowerCase() + " is not supported yet.");
        }
    }

}
