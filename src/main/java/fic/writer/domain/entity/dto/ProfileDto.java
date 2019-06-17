package fic.writer.domain.entity.dto;

import fic.writer.domain.entity.Profile;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    @NotBlank
    @Length(min = 2, max = 255)
    private String username;
    private String about;
    private String information;
    @Email(regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
    @NotBlank
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
