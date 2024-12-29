package logistics.dtos;

import logistics.entities.Warehouse;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class WarehouseDTO implements Serializable {
    private Long id;
    private String name;
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

    public void setId(Long id) {
        this.id = id;
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

    public static Warehouse toEntity(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse(
                warehouseDTO.getName(),
                warehouseDTO.getLocation()
        );
        warehouse.setId(warehouseDTO.getId());
        return warehouse;
    }

    public static List<WarehouseDTO> fromEntity(List<Warehouse> warehouses) {
        return warehouses.stream().map(WarehouseDTO::fromEntity).collect(Collectors.toList());
    }

    public static List<Warehouse> toEntity(List<WarehouseDTO> warehouseDTOs) {
        return warehouseDTOs.stream().map(WarehouseDTO::toEntity).collect(Collectors.toList());
    }
}
