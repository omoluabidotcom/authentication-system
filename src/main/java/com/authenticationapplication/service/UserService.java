package com.authenticationapplication.service;

import com.authenticationapplication.entity.User;
import com.authenticationapplication.entity.VerificationToken;
import com.authenticationapplication.model.PasswordModel;
import com.authenticationapplication.model.UserModel;

import java.util.Optional;

public interface UserService {

    User registerNewUser(UserModel userModel);

    void saveVerificationToken(User user, String token);

    String confirmUser(String token);

    VerificationToken generateVerificationToken(String token);

    User findUserByEmail(String email);

    void createPasswordResetToken(User user, String token);

    String findPasswordByResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    boolean validateOldPassword(User user, String oldPassword);
}
