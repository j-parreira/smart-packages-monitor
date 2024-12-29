package logistics.dtos;

import logistics.entities.Manager;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerDTO implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String password;
    private WarehouseDTO warehouse;
    private String office;

    public ManagerDTO() {
    }

    public ManagerDTO(Long id, String name, String email, String password, WarehouseDTO warehouse, String office) {
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

    public void setId(Long id) {
        this.id = id;
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

    public WarehouseDTO getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseDTO warehouse) {
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
                WarehouseDTO.fromEntity(manager.getWarehouse()),
                manager.getOffice());
    }

    public static Manager toEntity(ManagerDTO managerDTO) {
        Manager manager = new Manager(
                managerDTO.getName(),
                managerDTO.getEmail(),
                managerDTO.getPassword(),
                WarehouseDTO.toEntity(managerDTO.getWarehouse()),
                managerDTO.getOffice()
        );
        manager.setId(managerDTO.getId());
        return manager;
    }

    public static List<ManagerDTO> fromEntity(List<Manager> managers) {
        return managers.stream().map(ManagerDTO::fromEntity).collect(Collectors.toList());
    }

    public static List<Manager> toEntity(List<ManagerDTO> managerDTOs) {
        return managerDTOs.stream().map(ManagerDTO::toEntity).collect(Collectors.toList());
    }
}
