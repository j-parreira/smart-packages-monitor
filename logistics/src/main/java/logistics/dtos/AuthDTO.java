package logistics.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class AuthDTO implements Serializable {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public AuthDTO() {
    }

    public AuthDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }
}
