package com.example.yachting.security.auth.register;

import com.example.yachting.exception.exceptions.CustomException;
import com.example.yachting.exception.exceptions.ResourceAlreadyExistsException;
import com.example.yachting.exception.exceptions.TransactionFailedException;
import com.example.yachting.security.auth.authority.UserAuthorityRepository;
import com.example.yachting.security.auth.user.User;
import com.example.yachting.security.auth.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final static String USER_WITH_USERNAME_ALREADY_EXISTS = "User with username %s already exists.";
    private final static String USER_WITH_EMAIL_ALREADY_EXISTS = "User with email %s already exists.";
    private final static String USER_NOT_SAVED_MESSAGE = "User with username %s not saved.";
    private final static String AUTHORITY_NOT_SAVED_MESSAGE = "Authority for user %s not saved.";
    private final static String AUTHORITY_SAVING_ERROR_IDS_NOT_EQUAL_MESSAGE = "User ids from saving user: %s and authority: %s are not equal";

    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegisterServiceImpl(UserRepository userRepository, UserAuthorityRepository userAuthorityRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userAuthorityRepository = userAuthorityRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public Long registerUser(RegisterCommand registerCommand) {
        // check if user with that username already exists
        boolean userWithSameUsernameExists = userRepository.findOneByUsername(registerCommand.username()).isPresent();
        if (userWithSameUsernameExists) {
            throw new ResourceAlreadyExistsException(
                    String.format(USER_WITH_USERNAME_ALREADY_EXISTS, registerCommand.username()));
        }

        // check if user with that email already exists
        boolean userWithSameEmailExists = userRepository.findOneByEmail(registerCommand.email()).isPresent();
        if (userWithSameEmailExists) {
            throw new ResourceAlreadyExistsException(
                    String.format(USER_WITH_EMAIL_ALREADY_EXISTS, registerCommand.email()));
        }

        // save user
        User user = createUserWithEncodedPassword(registerCommand);
        User savedUser = Optional.of(userRepository.save(user))
                .orElseThrow(() ->
                        new TransactionFailedException(
                                String.format(USER_NOT_SAVED_MESSAGE, registerCommand.username())));
        Long userIdFromSavedUser = savedUser.getId();

        // save user authority (ROLE_USER) - returns user_id
        Optional<Long> savedAuthority = Optional.of(userAuthorityRepository.insertUserAuthority(userIdFromSavedUser));
        if (savedAuthority.isEmpty()) {
            throw new TransactionFailedException(
                    String.format(AUTHORITY_NOT_SAVED_MESSAGE, userIdFromSavedUser));
        }
        Long userIdFromSavedAuthority = savedAuthority.get();

        // userId from savedUser and savedAuthority should be equal
        if (!userIdFromSavedUser.equals(userIdFromSavedAuthority)) {
            throw new CustomException(
                    String.format(AUTHORITY_SAVING_ERROR_IDS_NOT_EQUAL_MESSAGE, userIdFromSavedUser, userIdFromSavedAuthority));
        }

        return userIdFromSavedUser;
    }


    private User createUserWithEncodedPassword(final RegisterCommand registerCommand) {
        User user = new User(
                registerCommand.username(),
                registerCommand.email(),
                bCryptPasswordEncoder.encode(registerCommand.password()),
                new HashSet<>()
        );
        return user;
    }


}
