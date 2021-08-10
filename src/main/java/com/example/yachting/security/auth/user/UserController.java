package com.example.yachting.security.auth.user;

import com.example.yachting.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.yachting.security.SecurityUtils.getCurrentUserUsername;

/**
 * User controller.
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"${client.origin}"})
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Gets current user.
     * @return response entity containing UserDTO.
     */
    @GetMapping("/current-user")
    public ResponseEntity<UserDTO> currentUser(){
        String username = getCurrentUserUsername()
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found."));

        UserDTO userDTO = userService.getUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    /**
     * Counts all users.
     * @return total number of users.
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        Long count = userService.countAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

}
