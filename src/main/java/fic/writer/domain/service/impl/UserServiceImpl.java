package fic.writer.domain.service.impl;

import fic.writer.domain.entity.User;
import fic.writer.domain.entity.dto.UserDto;
import fic.writer.domain.repository.UserRepository;
import fic.writer.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User create(UserDto userDto) {
        User user = User.builder().build();
        flushUserDtoToUser(user, userDto);
        return userRepository.save(user);

    }

    @Override
    public User update(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        flushUserDtoToUser(user, userDto);
        userRepository.save(user);
        return user;
    }

    private void flushUserDtoToUser(User user, UserDto userDto) {
        if (userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }
        if (userDto.getAbout() != null) {
            user.setAbout(userDto.getAbout());
        }
        if (userDto.getInformation() != null) {
            user.setInformation(userDto.getInformation());
        }
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
