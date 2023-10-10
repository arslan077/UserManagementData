package com.user.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contactdetails",
        indexes = {
                @Index(name = "idx_designation", columnList = "designation"),
                @Index(name = "idx_office_number", columnList = "office_number")
//                @Index(name = "idx_c_id", columnList = "c_id")

        })
public class ContactDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_id")
    private Long cd_id;
    @Column(name = "designation")
    private String designation;
    @Column(name = "office_number")
    private String officeNumber;


//
//    @ManyToOne
//    @JoinColumn(name = "c_id")
//    private Contact contact;
}
