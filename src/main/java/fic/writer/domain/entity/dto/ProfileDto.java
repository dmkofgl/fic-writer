package fic.writer.domain.entity.dto;

import fic.writer.domain.entity.Profile;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private String username;
    private String about;
    private String information;
    private String email;

    public static ProfileDto of(Profile profile) {
        return builder()
                .username(profile.getUsername())
                .about(profile.getAbout())
                .information(profile.getInformation())
                .email(profile.getEmail())
                .build();
    }
}
