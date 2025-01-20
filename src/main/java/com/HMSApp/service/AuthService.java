package com.HMSApp.service;

import com.HMSApp.entity.User;
import com.HMSApp.payload.LoginDto;
import com.HMSApp.payload.UserDto;
import com.HMSApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public ResponseEntity<?> registerUser(UserDto userDto) {

        // Check if username exists
        Optional<User> opUsername = userRepository.findByUsername(userDto.getUsername());
        if (opUsername.isPresent()) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }

        // Check if email exists
        Optional<User> opEmail = userRepository.findByEmail(userDto.getEmail());
        if (opEmail.isPresent()) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        // Check if mobile number exists
        Optional<User> opMobile = userRepository.findByMobile(userDto.getMobile());
        if (opMobile.isPresent()) {
            return new ResponseEntity<>("Mobile number already exists", HttpStatus.BAD_REQUEST);
        }

        User user = mapToEntity(userDto);

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        user.setRole("ROLE_USER");

        // Save the new user to the database
        userRepository.save(user);
        UserDto dto = mapToDto(user);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    public ResponseEntity<?> login(LoginDto loginDto) {
        String token = userService.verifyLogin(loginDto);
        if (token != null) {
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setName(user.getName());
        dto.setMobile(user.getMobile());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        return dto;
    }


    User mapToEntity(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setMobile(dto.getMobile());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public ResponseEntity<?> registerPropertyOwner(UserDto userDto) {
        // Check if username exists
        Optional<User> opUsername = userRepository.findByUsername(userDto.getUsername());
        if (opUsername.isPresent()) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }

        // Check if email exists
        Optional<User> opEmail = userRepository.findByEmail(userDto.getEmail());
        if (opEmail.isPresent()) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        // Check if mobile number exists
        Optional<User> opMobile = userRepository.findByMobile(userDto.getMobile());
        if (opMobile.isPresent()) {
            return new ResponseEntity<>("Mobile number already exists", HttpStatus.BAD_REQUEST);
        }

        User user = mapToEntity(userDto);

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        user.setRole("ROLE_OWNER");

        // Save the new user to the database
        userRepository.save(user);
        UserDto dto = mapToDto(user);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    public ResponseEntity<?> registerBlogManager(UserDto userDto) {
        // Check if username exists
        Optional<User> opUsername = userRepository.findByUsername(userDto.getUsername());
        if (opUsername.isPresent()) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }

        // Check if email exists
        Optional<User> opEmail = userRepository.findByEmail(userDto.getEmail());
        if (opEmail.isPresent()) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        // Check if mobile number exists
        Optional<User> opMobile = userRepository.findByMobile(userDto.getMobile());
        if (opMobile.isPresent()) {
            return new ResponseEntity<>("Mobile number already exists", HttpStatus.BAD_REQUEST);
        }

        User user = mapToEntity(userDto);

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        user.setRole("ROLE_BLOGMANAGER");

        // Save the new user to the database
        userRepository.save(user);
        UserDto dto = mapToDto(user);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}