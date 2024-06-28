package org.webapp.oauth2security.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.webapp.oauth2security.entity.User;
import org.webapp.oauth2security.repository.UserRepository;
import org.webapp.oauth2security.service.EmailService;
import org.webapp.oauth2security.service.UserService;

import java.util.Random;

@RestController
@AllArgsConstructor
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private UserService userService;

    private EmailService emailService;

    private PasswordEncoder passwordEncoder;

    @PostMapping("/auth/register")
    public User register(@RequestBody User user) throws Exception {
        User user1= userRepository.findByEmail(user.getEmail());
        if(user1!=null){
            throw new Exception("this email is associated with another account");
        }

        User createUser=new User();
        createUser.setEmail(user.getEmail());
        createUser.setFirstName(user.getFirstName());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.setLastName(user.getLastName());
        createUser.setVerified(false);
        String otp= generateOtp();
        createUser.setOtp(otp);

        User saveUser=userRepository.save(createUser);
        sendVerificationEmail(saveUser.getEmail(), otp);
        return saveUser;

    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam String email, @RequestParam String otp){
        try{
            userService.verifyUser(email,otp);
            return new ResponseEntity<>("user verified Successfully", HttpStatus.OK);
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    public String generateOtp(){
        Random random=new Random();
        int otpValue= 100000 + random.nextInt(900000);
        return String.valueOf(otpValue);
    }

    public void sendVerificationEmail(String email,String otp){
        String subject="email verification";
        String body="your otp for verification is : "+otp;

        emailService.sendEmail(email,subject,body);
    }
}
