package fic.writer.domain.service;

import fic.writer.domain.entity.User;
import fic.writer.domain.entity.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Page<User> findPage(Pageable pageable);

    Optional<User> findById(Long aLong);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User create(UserDto user);

    User addBookAsAuthor(Long userId, Long bookId);

    User update(Long userId, UserDto user);

    void delete(User user);

    void deleteById(Long aLong);
}
