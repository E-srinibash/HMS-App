package com.HMSApp.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    @NotBlank
    @Size(min = 3,message = "Name should be at least 3 characters long")
    private String name;

    @NotBlank
    @Size(min = 6, message = "Username should be at least 6 characters long")
    @Column(unique = true)
    private String username;

    @Email
    private String email;


    @NotBlank
    @Size(min = 6,max = 10 ,message = "Name should be at least 6 characters long ")
    private String password;

    @Size(min = 10, max = 10 ,message = "number should be of size 10")
    @Column(unique = true)
    private String mobile;

}
