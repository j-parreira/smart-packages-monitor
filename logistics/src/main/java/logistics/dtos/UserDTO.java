package logistics.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import logistics.entities.User;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private String name;
    private String email;
    private String password;
    private String role;

    public UserDTO() {
    }

    public UserDTO(String name, String email, String password, String role ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static UserDTO from(User user) {
        return new UserDTO(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                Hibernate.getClass(user).getSimpleName());
    }

    public static List<UserDTO> from (List<User> users) {
        return users.stream().map(UserDTO::from).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
