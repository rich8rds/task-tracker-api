package com.richards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.richards.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String title;
    @Column(length = 300)
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Date createdAt;
    private Date updatedAt;
    private Date completedAt;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;


    @PrePersist
    public void prePersist() {
        createdAt = new Date();
        status = Status.PENDING;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
        if(status.equals(Status.COMPLETED)){
            completedAt = new Date();
        }
    }
}
