package logistics.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import logistics.entities.Employee;
import logistics.entities.Volume;
import logistics.entities.Warehouse;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDTO implements Serializable {
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

    private List<Volume> volumes;

    public EmployeeDTO() {
        this.volumes = new LinkedList<>();
    }

    public EmployeeDTO(Long id, String name, String email, String password, Warehouse warehouse) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.warehouse = warehouse;
        this.volumes = new LinkedList<>();
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

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

    public static EmployeeDTO fromEntity(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getPassword(),
                employee.getWarehouse()
        );
    }

    public static List<EmployeeDTO> fromEntity(List<Employee> employees) {
        return employees.stream().map(EmployeeDTO::fromEntity).collect(Collectors.toList());
    }
}
