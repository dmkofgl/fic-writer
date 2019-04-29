package fic.writer.web.config.security.oauth.externalService;


import fic.writer.domain.entity.auth.OauthUserDetails;
import fic.writer.domain.service.OauthUserService;
import fic.writer.domain.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class OauthExternalServerAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private OAuth2ClientContext oauth2ClientContext;
    @Autowired
    private OauthUserService oauthUserService;
    @Autowired
    private ProfileService profileService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        String username = oAuth2Authentication.getName();
        if (!profileService.findByUsername(username).isPresent()) {
            OauthUserDetails oauthUserDetails = createNewOauthUser(oAuth2Authentication.getUserAuthentication());
            SecurityContextHolder.getContext().setAuthentication(oAuth2Authentication);
        }
//        OAuth2AccessToken token = getTokenGranter().grant(tokenRequest.getGrantType(), tokenRequest);
        httpServletResponse.getWriter().write("token:" + oauth2ClientContext.getAccessTokenRequest());
    }

    private OauthUserDetails createNewOauthUser(Authentication authentication) {
        Long id = new Long(((Map) authentication.getDetails()).get("id").toString());
        OauthUserDetails oauthUserDetails = new OauthUserDetails();

        return oauthUserDetails;
    }

}

