package fic.writer.domain.entity.dto;

import fic.writer.domain.entity.User;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;
    private String about;
    private String information;

    public static UserDto of(User user) {
        return builder()
                .username(user.getUsername())
                .about(user.getAbout())
                .information(user.getInformation())
                .build();
    }
}
