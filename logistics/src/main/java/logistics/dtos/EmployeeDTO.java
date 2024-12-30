package logistics.dtos;

import logistics.ejbs.WarehouseBean;
import logistics.entities.Employee;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDTO implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Long warehouseId;
    private List<VolumeDTO> volumes;

    public EmployeeDTO() {
        this.volumes = new LinkedList<>();
    }

    public EmployeeDTO(Long id, String name, String email, String password, Long warehouseId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.warehouseId = warehouseId;
        this.volumes = new LinkedList<>();
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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<VolumeDTO> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<VolumeDTO> volumes) {
        this.volumes = volumes;
    }

    public static EmployeeDTO fromEntity(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                null,
                employee.getWarehouse().getId()
        );
    }

//    public static Employee toEntity(EmployeeDTO employeeDTO) {
//        Employee employee = new Employee(
//                employeeDTO.getName(),
//                employeeDTO.getEmail(),
//                employeeDTO.getPassword(),
//                WarehouseBean.find(employeeDTO.getWarehouseId())
//        );
//        employee.setId(employeeDTO.getId());
//        return employee;
//    }

    public static List<EmployeeDTO> fromEntity(List<Employee> employees) {
        return employees.stream().map(EmployeeDTO::fromEntity).collect(Collectors.toList());
    }

//    public static List<Employee> toEntity(List<EmployeeDTO> employeeDTOs) {
//        return employeeDTOs.stream().map(EmployeeDTO::toEntity).collect(Collectors.toList());
//    }
}
