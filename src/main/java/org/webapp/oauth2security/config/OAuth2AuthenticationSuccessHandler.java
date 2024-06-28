package org.webapp.oauth2security.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.webapp.oauth2security.entity.User;
import org.webapp.oauth2security.repository.UserRepository;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;
    Logger logger= LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("OAuth2AuthenticationSuccessHandler");

        DefaultOAuth2User user= (DefaultOAuth2User) authentication.getPrincipal();

//        logger.info(user.getName());
//
//        user.getAttributes().forEach((key,value)->{
//            logger.info("{}  =>  {}",key,value);
//        });
//
//        logger.info(user.getAuthorities().toString());

        String firstName= user.getAttribute("name");
        String email=user.getAttribute("email").toString();
        String password=("password");

        User newUser=new User();
        newUser.setEmail(email);
        newUser.setFirstName(firstName);
        newUser.setPassword(password);
        newUser.setVerified(true);
        User dbUser=userRepository.findByEmail(email);
        if(dbUser==null){
            userRepository.save(newUser);
        }

        new DefaultRedirectStrategy().sendRedirect(request,response,"/api/user");
    }
}
