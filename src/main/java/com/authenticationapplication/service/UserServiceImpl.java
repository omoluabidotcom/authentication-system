package com.authenticationapplication.service;

import com.authenticationapplication.entity.User;
import com.authenticationapplication.entity.VerificationToken;
import com.authenticationapplication.model.UserModel;
import com.authenticationapplication.repository.UserRepository;
import com.authenticationapplication.repository.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerNewUser(UserModel userModel) {

        User user = new User();
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setEmail(userModel.getEmail());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        return user;
    }

    @Override
    public void saveVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationRepository.save(verificationToken);
    }

    @Override
    public String confirmUser(String token) {

        VerificationToken verifiedUser = verificationRepository.findByToken(token);

        if(verifiedUser == null) {
            return "Invalid";
        }

        User user = verifiedUser.getUser();
        Calendar cal = Calendar.getInstance();

        if((verifiedUser.getExpirationTime().getTime() - cal.getTime().getTime()) <=0) {
            verificationRepository.delete(verifiedUser);
            return "Expired";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "Valid";
    }

}
