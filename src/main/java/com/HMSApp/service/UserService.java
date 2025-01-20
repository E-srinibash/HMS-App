package com.HMSApp.service;

import com.HMSApp.entity.User;
import com.HMSApp.payload.LoginDto;
import com.HMSApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    public String verifyLogin (LoginDto loginDto){
        Optional<User> opUser = userRepository.findByUsername(loginDto.getUsername());

        if(opUser.isPresent()) {
            User user = opUser.get();

            if (BCrypt.checkpw(loginDto.getPassword(), user.getPassword())) {
                String token = jwtService.generateToken(user.getUsername());
                return token;
            }
            return null;
        }
        return null;
    }

}
