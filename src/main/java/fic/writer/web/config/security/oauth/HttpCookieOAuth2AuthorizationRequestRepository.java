package fic.writer.web.config.security.oauth;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import fic.writer.domain.utils.CookieUtils;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HttpCookieOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    private static final int cookieExpireSeconds = 180;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest httpServletRequest) {
        return CookieUtils.getCookie(httpServletRequest, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest oAuth2AuthorizationRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (oAuth2AuthorizationRequest == null) {
            cleanAuthCookie(httpServletRequest, httpServletResponse);
        } else {
            addAuthorizationRequestInCookie(oAuth2AuthorizationRequest, httpServletResponse);
            addRedirectUrlInRequestIfExist(httpServletRequest, httpServletResponse);
        }
    }

    public void cleanAuthCookie(HttpServletRequest req, HttpServletResponse res) {
        CookieUtils.deleteCookie(req, res, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtils.deleteCookie(req, res, REDIRECT_URI_PARAM_COOKIE_NAME);
    }

    private void addAuthorizationRequestInCookie(OAuth2AuthorizationRequest authorizationRequest, HttpServletResponse httpServletResponse) {
        String serializedRequest = CookieUtils.serialize(authorizationRequest);
        CookieUtils.addCookie(httpServletResponse, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, serializedRequest, cookieExpireSeconds);
    }

    private void addRedirectUrlInRequestIfExist(HttpServletRequest request, HttpServletResponse response) {
        String redirectUriAfterLogin = getRedirectUrlFromRequest(request);
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, cookieExpireSeconds);
        }
    }

    private String getRedirectUrlFromRequest(HttpServletRequest request) {
        return request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);

    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest httpServletRequest) {
        return this.loadAuthorizationRequest(httpServletRequest);
    }

}
