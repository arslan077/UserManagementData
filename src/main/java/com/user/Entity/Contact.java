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
@Table(name = "contact",
        indexes = {
                @Index(name = "idx_email", columnList = "email"),
                @Index(name = "idx_contact_name", columnList = "contact_name"),
                @Index(name = "idx_userId", columnList = "user_id"),
                @Index(name = "idx_cd_id", columnList = "cd_id")

        })
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long CId;
    @Column(name = "email")
    private String email;
    @Column(name = "contact_name")
    private String contactName;
    @Column(name = "user_id")
    private Long userId;


    @ManyToOne
    @JoinColumn(name = "cd_id")
    private ContactDetails contactDetails;

}

