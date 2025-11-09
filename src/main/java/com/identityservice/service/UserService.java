package com.identityservice.service;

import com.identityservice.common.exception.AppException;
import com.identityservice.common.exception.ErrorCode;
import com.identityservice.entity.User;
import com.identityservice.mapper.UserMapper;
import com.identityservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    // private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new user if the username does not already exist.
     *
     * @param request user data to be created
     * @return the created {@link User} entity
     * @throws AppException if the username already exists or a database error occurs
     */
    @Transactional
    public User create(User request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        //   request.setPassword(passwordEncoder.encode(request.getPassword()));
        request.setCreateAt(new Date());
        request.setUdpateAt(new Date());

        try {
            return userRepository.save(request);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }
    }

    /**
     * Retrieves all users from the database.
     *
     * @return list of {@link User} entities
     * @throws AppException if a database access error occurs
     */
    public List<User> getAll() {
        try {
            return userRepository.findAll();
        } catch (Exception ex) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }
    }

    /**
     * Retrieves user information by username.
     *
     * @param username the username to search for
     * @return the found {@link User} entity
     * @throws AppException if the user is not found or a database error occurs
     */
    public User getUserInfo(String username) {
        try {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        } catch (AppException ex) {
            throw ex; // Re-throw known application exceptions
        } catch (Exception ex) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }
    }

    /**
     * Updates existing user information.
     *
     * @param username   the username of the user to update
     * @param updateData new user information
     * @return the updated {@link User} entity
     * @throws AppException if the user is not found or a database error occurs
     */
    @Transactional
    public Void update(String username, User updateData) {

        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        try {

            userMapper.toUser(existingUser, updateData);
            existingUser.setUdpateAt(new Date());

            userRepository.save(existingUser);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }
        return null;
    }

    /**
     * Deletes a user by username.
     *
     * @param username the username of the user to delete
     * @throws AppException if the user does not exist or a database error occurs
     */
    @Transactional
    public Void delete(String username) {
        if (!userRepository.existsByUsername(username)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        try {
            userRepository.deleteByUsername(username);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }

        return null;
    }
}
