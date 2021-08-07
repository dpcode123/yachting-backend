package com.example.yachting.security.auth.user;

/**
 * User service.
 */
public interface UserService {
    /**
     * Gets user by username.
     * @param username
     * @return UserDTO
     */
    UserDTO getUserByUsername(String username);

    /**
     * Counts all users.
     * @return total number of users.
     */
    Long countAllUsers();
}
