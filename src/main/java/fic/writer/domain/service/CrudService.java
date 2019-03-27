package fic.writer.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    List<T> findAll();

    Page<T> findPage(Pageable pageable);

    Optional<T> findById(ID id);

    T save(T t);

    void delete(T t);

    void deleteById(ID id);
}
