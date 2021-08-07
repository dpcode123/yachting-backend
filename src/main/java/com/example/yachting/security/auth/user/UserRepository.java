package com.example.yachting.security.auth.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Counts all users.
     * @return total number of users.
     */
    Long countAllBy();

    /**
     * Gets single user by username.
     * @param username
     * @return user wrapped in an Optional.
     */
    Optional<User> findOneByUsername(String username);

    /**
     * Gets single user by email.
     * @param email
     * @return user wrapped in an Optional.
     */
    Optional<User> findOneByEmail(String email);


}
