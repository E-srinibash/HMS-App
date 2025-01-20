package com.HMSApp.controller;


import com.HMSApp.entity.JwtToken;
import com.HMSApp.entity.User;
import com.HMSApp.payload.LoginDto;
import com.HMSApp.payload.ProfileDto;
import com.HMSApp.payload.UserDto;
import com.HMSApp.repository.UserRepository;
import com.HMSApp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        return authService.registerUser(userDto);

    }

    @PostMapping("/property/sign-up")
    public ResponseEntity<?> createPropertyOwnerAccount(@RequestBody UserDto userDto) {
        return authService.registerPropertyOwner(userDto);

    }

    @PostMapping("/blog/sign-up")
    public ResponseEntity<?> createBlogManagerAccount(@RequestBody UserDto userDto) {
        return authService.registerBlogManager(userDto);

    }

    @PostMapping("/login")
    public ResponseEntity<?> verify(@RequestBody LoginDto loginDto) {
        String token = userService.verifyLogin(loginDto);
        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(token);
        jwtToken.setType("JWT");
        if (token != null) {
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<ProfileDto> getUserProfile(
            @AuthenticationPrincipal User user
    ) {
        ProfileDto dto = new ProfileDto();
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/login-otp")
    public ResponseEntity<?> loginWithOtp(@RequestParam String mobile) {
        String otp = otpService.generateOTP(mobile);
        if (otp != null) {
            smsService.sendSms("+91" + mobile, "Your otp for login is " + otp);
            return new ResponseEntity<>(otp, HttpStatus.OK);

        }
        return new ResponseEntity<>("invalid", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("verify-otp")
    public ResponseEntity<String> verifyOtp(
            @RequestParam String mobile,
            @RequestParam String otp
    ) {
        boolean valid = otpService.validateOTP(mobile, otp);

        if (valid) {
            Optional<User> user = userRepository.findByMobile(mobile);
            if (user.isPresent()) {
                String token = jwtService.generateToken(user.get().getUsername());

                return new ResponseEntity<>(token, HttpStatus.OK);
            }
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
    }
}