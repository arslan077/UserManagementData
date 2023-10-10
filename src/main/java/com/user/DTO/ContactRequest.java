package com.user.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContactRequest {

    @NotBlank(message = "email cannot be empty")
    private String email;

    @NotBlank(message = "contactName cannot be empty")
    @Size(min = 3, max = 15, message = "contactName size must be between 3 and 15 characters long")
    private String contactName;
    private Long userId;
    @NotBlank(message = "designation cannot be empty")
    @Size(min = 4, max = 20, message = "designation size must be between 4 and 20 characters long")
    private String designation;
    @NotBlank(message = "officeNumber cannot be empty")
    @Size(min = 4, max = 14, message = "officeNumber size must be between 4 and 14 characters long")
    private String officeNumber;





}