package com.ecommerce.library.dto;

import com.ecommerce.library.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
    @Size(min = 3 , max = 10, message = "Invalid firstname !(3-10 characters)")
    private String firstname;
    @Size(min = 3 , max = 10, message = "Invalid lastname !(3-10 characters)")
    private String lastname;
    private String username;
    @Size(min = 5 , max = 15, message = "Invalid password !(5-15 characters)")
    private String password;

    private String repeatPassword;
}