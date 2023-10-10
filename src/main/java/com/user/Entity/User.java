package com.user.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import jakarta.persistence.*;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "user",
        indexes = {
                @Index(name = "idx_username", columnList = "username"),
                @Index(name = "idx_phone", columnList = "phone")

        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "username")
    private String username;
    @Column(name = "phone")
    private String phone;




    public User(long userId, String username, String phone) {
        this.userId =userId;
        this.username=username;
        this.phone=phone;


    }

}
