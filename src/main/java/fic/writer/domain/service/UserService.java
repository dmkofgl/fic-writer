package fic.writer.domain.service;

import fic.writer.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<User> findAll();

    Page<User> findPage(Pageable pageable);

    Optional<User> findById(UUID uuid);
}
