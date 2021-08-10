package com.example.yachting.security.auth.user;

import com.example.yachting.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * User service implementation.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final static String USER_NOT_FOUND_MESSAGE = "User with username %s not found.";

    private final UserRepository userRepository;

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException if user is not found
     */
    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findOneByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(USER_NOT_FOUND_MESSAGE, username)));

        Set<String> grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map(authority -> authority.getName())
                .collect(Collectors.toSet());

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                grantedAuthorities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long countAllUsers() {
        return userRepository.countAllBy();
    }

}
