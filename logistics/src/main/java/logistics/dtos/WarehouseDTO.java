package logistics.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import logistics.entities.Warehouse;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class WarehouseDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    @Size(min = 3, max = 100)
    private String location;

    private List<EmployeeDTO> employees;
    private List<StockDTO> stocks;

    public WarehouseDTO() {
        this.employees = new LinkedList<>();
        this.stocks = new LinkedList<>();
    }

    public WarehouseDTO(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.employees = new LinkedList<>();
        this.stocks = new LinkedList<>();
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }

    public List<StockDTO> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockDTO> stocks) {
        this.stocks = stocks;
    }

    public static WarehouseDTO fromEntity(Warehouse warehouse) {
        return new WarehouseDTO(
                warehouse.getId(),
                warehouse.getName(),
                warehouse.getLocation()
        );
    }

    public static List<WarehouseDTO> fromEntity(List<Warehouse> warehouses) {
        return warehouses.stream().map(WarehouseDTO::fromEntity).collect(Collectors.toList());
    }
}
