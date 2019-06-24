package fic.writer.domain.entity.auth.profileInfo;

import java.util.Map;

public abstract class OAuth2ProfileInfo {
    protected Map<String, Object> attributes;

    public OAuth2ProfileInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
