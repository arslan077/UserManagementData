package com.user.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Vehicle {

    private Long vId;

    private String vehicleName;

    private String vehicleType;

    private Long userId;
}
