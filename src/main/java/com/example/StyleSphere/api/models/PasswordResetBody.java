package com.example.StyleSphere.api.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PasswordResetBody {

    @NotBlank
    @NotNull
    private String token;

    @NotNull
    @NotBlank
    @Size(min = 6, max = 15)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
    private String Password;

    public @NotBlank @NotNull String getToken() {
        return token;
    }

    public void setToken(@NotBlank @NotNull String token) {
        this.token = token;
    }

    public @NotNull @NotBlank @Size(min = 6, max = 15) @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$") String getPassword() {
        return Password;
    }

    public void setPassword(@NotNull @NotBlank @Size(min = 6, max = 15) @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$") String password) {
        Password = password;
    }
}
