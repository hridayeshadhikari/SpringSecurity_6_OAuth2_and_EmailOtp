package org.webapp.oauth2security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webapp.oauth2security.entity.User;
import org.webapp.oauth2security.repository.UserRepository;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public void verifyUser(String email,String otp){
        User user=userRepository.findByEmail(email);
        if (user==null){
           throw new RuntimeException("user is null");
        } else if (user.getVerified()==true) {
            throw new RuntimeException("user is already verified");
        } else if (otp.equals(user.getOtp())) {
            user.setVerified(true);
            userRepository.save(user);
        }
        else {
            throw new RuntimeException("Internal server error");
        }

    }


}
