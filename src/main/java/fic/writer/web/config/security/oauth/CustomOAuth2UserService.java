package fic.writer.web.config.security.oauth;

import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.auth.AuthProvider;
import fic.writer.domain.entity.auth.OauthProfileDetails;
import fic.writer.domain.entity.auth.profileInfo.OAuth2ProfileInfo;
import fic.writer.domain.entity.auth.profileInfo.OAuth2ProfileInfoFactory;
import fic.writer.domain.repository.OauthProfileDetailsRepository;
import fic.writer.domain.repository.ProfileRepository;
import fic.writer.exception.OAuth2AuthenticationProcessingException;
import fic.writer.web.config.security.authorization.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private ProfileRepository profileRepository;
    private OauthProfileDetailsRepository profileDetailsRepository;

    @Autowired
    public CustomOAuth2UserService(ProfileRepository profileRepository, OauthProfileDetailsRepository profileDetailsRepository) {
        this.profileRepository = profileRepository;
        this.profileDetailsRepository = profileDetailsRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        String authProviderName = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        AuthProvider authProvider = AuthProvider.valueOf(authProviderName.toUpperCase());

        OAuth2ProfileInfo userInfo = OAuth2ProfileInfoFactory.getOAuth2ProfileInfo(authProvider, oAuth2User.getAttributes());
        assertEmailExist(userInfo);

        return createOAuth2User(userInfo, authProvider);
    }

    private void assertEmailExist(OAuth2ProfileInfo userInfo) {
        if (StringUtils.isEmpty(userInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
    }

    private OAuth2User createOAuth2User(OAuth2ProfileInfo userInfo, AuthProvider authProvider) {
        Optional<OauthProfileDetails> userOptional = profileDetailsRepository.findByProfileEmail(userInfo.getEmail());
        OauthProfileDetails user = userOptional
                .map(u -> checkEqualsProvider(u, authProvider))
                .map(x -> updateExistingUser(x, userInfo))
                .orElseGet(() -> registerNewProfile(authProvider, userInfo));
        return UserPrincipal.create(user, userInfo.getAttributes());
    }

    private OauthProfileDetails registerNewProfile(AuthProvider authProvider, OAuth2ProfileInfo oAuth2ProfileInfo) {
        Profile user = Profile.builder()
                .username(oAuth2ProfileInfo.getName())
                .email(oAuth2ProfileInfo.getEmail())
                .imageUrl(oAuth2ProfileInfo.getImageUrl())
                .build();
        OauthProfileDetails details = OauthProfileDetails.builder()
                .profile(user)
                .provider(authProvider)
                .providerId(oAuth2ProfileInfo.getId())
                .build();

        return profileDetailsRepository.save(details);
    }

    private OauthProfileDetails updateExistingUser(OauthProfileDetails existingUser, OAuth2ProfileInfo oAuth2ProfileInfo) {
        Profile profile = existingUser.getProfile();
        profile.setUsername(oAuth2ProfileInfo.getName());
        profile.setImageUrl(oAuth2ProfileInfo.getImageUrl());
        profileRepository.save(profile);
        return profileDetailsRepository.save(existingUser);
    }

    private OauthProfileDetails checkEqualsProvider(OauthProfileDetails user, AuthProvider authProvider) {
        assertEqualsProvider(user, authProvider);
        return user;
    }

    private void assertEqualsProvider(OauthProfileDetails user, AuthProvider authProvider) {
        if (!user.getProvider().equals(authProvider)) {
            String exceptionMessage = "Looks like you're signed up with " +
                    user.getProvider() + " account. Please use your " + user.getProvider() +
                    " account to login.";
            throw new OAuth2AuthenticationProcessingException(exceptionMessage);
        }
    }


}
