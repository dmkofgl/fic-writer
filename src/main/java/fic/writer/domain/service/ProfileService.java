package fic.writer.domain.service;

import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.dto.ProfileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProfileService {
    List<Profile> findAll();

    Page<Profile> findPage(Pageable pageable);

    Optional<Profile> findById(Long aLong);

    Optional<Profile> findByUsername(String username);

    Optional<Profile> findByEmail(String email);

    Profile create(ProfileDto user);

    Profile addBookAsAuthor(Long userId, Long bookId);

    Profile update(Long userId, ProfileDto user);

    Profile save(Profile profile);

    void delete(Profile profile);

    void deleteById(Long aLong);

    Optional<Profile> findByUsernameOrEmail(String usernameOrEmail);
}
