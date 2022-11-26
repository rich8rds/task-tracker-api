package com.richards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.richards.enums.Gender;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(name = "email", columnNames = {"email"}) })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date createdAt;
    private Date updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Task> tasks;

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }

}
