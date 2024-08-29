package com.example.shop_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseModel {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", nullable = false, length = 50)
    private String fullName;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "address", nullable = true, length = 100)
    private String address;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    private  boolean active;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "facebook_account_id", length = 100)
    private String facebookAccountId;

    @Column(name = "google_account_id", length = 100)
    private String googleAccountId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
