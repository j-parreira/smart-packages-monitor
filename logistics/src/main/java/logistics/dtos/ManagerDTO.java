package logistics.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import logistics.entities.Manager;
import logistics.entities.Volume;
import logistics.entities.Warehouse;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Warehouse warehouse;

    @NotNull
    @Size(min = 3, max = 100)
    private String office;

    public ManagerDTO() {
    }

    public ManagerDTO(Long id, String name, String email, String password, Warehouse warehouse, String office) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.warehouse = warehouse;
        this.office = office;
    }

    public Long getId() {
        return id;
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

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public static ManagerDTO fromEntity(Manager manager) {
        return new ManagerDTO(
                manager.getId(),
                manager.getName(),
                manager.getEmail(),
                manager.getPassword(),
                manager.getWarehouse(),
                manager.getOffice());
    }

    public static List<ManagerDTO> fromEntity(List<Manager> managers) {
        return managers.stream().map(ManagerDTO::fromEntity).collect(Collectors.toList());
    }
}
