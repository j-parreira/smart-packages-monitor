package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQuery(
        name = "getAllWarehouses",
        query = "SELECT w FROM Warehouse w ORDER BY w.name"
)
@Table(name = "warehouses",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "warehouse")
    private List<Employee> employees;

    @OneToMany(mappedBy = "warehouse")
    private List<Stock> stocks;

    public Warehouse() {
        this.employees = new LinkedList<>();
        this.stocks = new LinkedList<>();
    }

    public Warehouse(String name) {
        this.name = name;
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public void removeStock(Stock stock) {
        this.stocks.remove(stock);
    }
}
