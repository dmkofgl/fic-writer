package fic.writer.web.config.security.authorization;

import fic.writer.domain.entity.auth.OauthProfileDetails;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class UserPrincipal implements OAuth2User, UserDetails {
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    private OauthProfileDetails profileDetails;


    public static UserPrincipal create(OauthProfileDetails details) {
        Assert.notNull(details, "ProfileDetails cannot be null");
        Assert.notNull(details.getProfile(), "Profile cannot be null");

        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return builder()
                .profileDetails(details)
                .authorities(authorities)
                .build();
    }

    public static UserPrincipal create(OauthProfileDetails user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public Long getId() {
        return profileDetails.getProfile().getId();
    }

    @Override
    public String getPassword() {
        return profileDetails.getPassword();
    }

    //Use email instead username, because different oauth provider can produce different username
    @Override
    public String getUsername() {
        return profileDetails.getProfile().getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(getId());
    }
}
