package com.example.StyleSphere.models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name ="verification_token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private LocalUser user;


    @Lob
    @Column(name ="token",nullable = false,unique = true)
    private String token;

    @Column(name ="created_timestamp", nullable = false)
    private Timestamp createdTimestamp;

    public LocalUser getUser() {
        return user;
    }

    public void setUser(LocalUser localUser) {
        this.user = localUser;
    }


    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
