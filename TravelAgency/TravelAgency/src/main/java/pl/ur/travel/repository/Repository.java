package pl.ur.travel.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Repository<T> {

    List<T> selectAll();

    void insert(T o);

    void deleteById(UUID id);

    Optional<T> selectById(UUID id);

    void updateById(UUID id, T o);

}
