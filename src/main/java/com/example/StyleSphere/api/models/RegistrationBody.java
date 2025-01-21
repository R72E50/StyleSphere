package com.example.StyleSphere.api.models;

import jakarta.validation.constraints.*;

public class RegistrationBody {

    @NotNull
    @NotBlank
    @Size(min = 6, max = 15)
    private String username;

    @Email
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 6, max = 15)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
    private String password;

    @NotNull
    @NotBlank
    private String firstname;

    @NotNull
    @NotBlank
    private String lastname;

    public @NotNull @NotBlank @Size(min = 6, max = 15) String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @NotBlank @Size(min = 6, max = 15) String username) {
        this.username = username;
    }

    public @Email @NotNull @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotNull @NotBlank String email) {
        this.email = email;
    }

    public @NotNull @NotBlank @Size(min = 6, max = 15) @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @NotBlank @Size(min = 6, max = 15) @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$") String password) {
        this.password = password;
    }

    public @NotNull @NotBlank String getFirstname() {
        return firstname;
    }

    public void setFirstName(@NotNull @NotBlank String firstname) {
        this.firstname = firstname;
    }

    public @NotNull @NotBlank String getLastname() {
        return lastname;
    }

    public void setLastName(@NotNull @NotBlank String lastname) {
        this.lastname = lastname;
    }
}
