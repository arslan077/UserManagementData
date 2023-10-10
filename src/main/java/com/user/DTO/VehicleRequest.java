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
public class VehicleRequest {

    @NotBlank(message = "vehicleName cannot be empty")
    @Size(min = 3, max = 32, message = "vehicleName must be between 3 and 32 characters long")
    private String vehicleName;

    @NotBlank(message = "vehicleType cannot be empty")
    @Size(min = 4, max = 8, message = "vehicleType size must be between 4 and 8 characters long")
    private String vehicleType;
    private Long userId;


}