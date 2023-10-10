package com.user.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicle",
        indexes = {
                @Index(name = "idx_vehicle_name", columnList = "vehicle_name"),
                @Index(name = "idx_vehicle_type", columnList = "vehicle_type"),
                @Index(name = "idx_user_id", columnList = "user_id")

        })
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "v_id")
    private Long VId;
    @Column(name = "vehicle_name")
    private String vehicleName;
    @Column(name = "vehicle_type")
    private String vehicleType;
    @Column(name = "user_id")
    private Long userId;


}


