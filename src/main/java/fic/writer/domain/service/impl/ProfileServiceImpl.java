package fic.writer.domain.service.impl;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.dto.ProfileDto;
import fic.writer.domain.repository.ProfileRepository;
import fic.writer.domain.service.ProfileService;
import fic.writer.domain.service.helper.flusher.ProfileFlusher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    public Page<Profile> findPage(Pageable pageable) {
        return profileRepository.findAll(pageable);
    }

    @Override
    public Optional<Profile> findById(Long id) {
        return profileRepository.findById(id);
    }

    @Override
    public Optional<Profile> findByUsername(String username) {
        return profileRepository.findByUsername(username);
    }

    @Override
    public Optional<Profile> findByEmail(String email) {
        return profileRepository.findByEmail(email);
    }

    @Override
    public Profile create(ProfileDto profileDto) {
        Profile profile = ProfileFlusher.convertProfileDtoToProfile(profileDto);
        return profileRepository.save(profile);
    }

    @Override
    public Profile update(Long userId, ProfileDto profileDto) {
        Profile profile = profileRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        ProfileFlusher.flushProfileDtoToProfile(profile, profileDto);
        profileRepository.save(profile);
        return profile;
    }

    @Override
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public void delete(Profile profile) {
        profileRepository.delete(profile);
    }

    @Override
    public void deleteById(Long id) {
        profileRepository.deleteById(id);
    }

    @Override
    public Profile addBookAsAuthor(Long userId, Long bookId) {
        Profile profile = profileRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Book book = Book.builder().id(bookId).build();
        profile.getBooksAsAuthor().add(book);
        return profile;
    }

    @Override
    public Optional<Profile> findByUsernameOrEmail(String usernameOrEmail) {
        Optional<Profile> profile;
        if (isEmail(usernameOrEmail)) {
            profile = findByEmail(usernameOrEmail);
        } else {
            profile = findByUsername(usernameOrEmail);
        }
        return profile;
    }

    private boolean isEmail(String source) {
        final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return source.matches(emailRegex);
    }


}
