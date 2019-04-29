package fic.writer.domain.service.helper;

import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.dto.ProfileDto;

public class ProfileFlusher {
    public static void flushProfileDtoToProfile(Profile profile, ProfileDto profileDto) {
        if (profileDto.getUsername() != null) {
            profile.setUsername(profileDto.getUsername());
        }
        if (profileDto.getAbout() != null) {
            profile.setAbout(profileDto.getAbout());
        }
        if (profileDto.getInformation() != null) {
            profile.setInformation(profileDto.getInformation());
        }
    }
}
