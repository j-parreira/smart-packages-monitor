package logistics.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(mappedBy = "warehouse")
    private List<Employee> employees;
    @OneToMany(mappedBy = "warehouse")
    private List<Product> products;
}
