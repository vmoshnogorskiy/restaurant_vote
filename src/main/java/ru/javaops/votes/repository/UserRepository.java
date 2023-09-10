package ru.javaops.votes.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.votes.error.NotFoundException;
import ru.javaops.votes.model.User;

import java.util.Optional;

import static ru.javaops.votes.config.SecurityConfig.PASSWORD_ENCODER;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {

    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
    @Cacheable(cacheManager = "defaultCacheManager", cacheNames = "users")
    Optional<User> findByEmailIgnoreCase(String email);

    default User prepareAndSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return save(user);
    }

    @Override
    @Modifying
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    User save(User user);

    default User getExistedByEmail(String email) {
        return findByEmailIgnoreCase(email).orElseThrow(() -> new NotFoundException("User with email=" + email + " not found"));
    }
}