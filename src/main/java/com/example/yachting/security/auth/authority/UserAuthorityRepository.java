package com.example.yachting.security.auth.authority;

import com.example.yachting.security.auth.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * User authority repository.
 * @author dp
 */
@Repository
public interface UserAuthorityRepository extends JpaRepository<User, Long> {
    /**
     * Adds new role USER for user, when user is registered.
     * Role ADMIN is added manually.
     * @param userId
     * @return user id
     */
    @Query(value = "INSERT INTO user_authority(user_id, authority_id)" +
            "VALUES (?1, 2)" +
            "RETURNING user_id",
            nativeQuery = true)
    Long insertUserAuthority(Long userId);



}
