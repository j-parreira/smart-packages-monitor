package logistics.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Employee extends User {
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    Warehouse warehouse;

    public Employee() {
    }

    public Employee(String name, String email, String password, Warehouse warehouse) {
        super(name, email, password);
        this.warehouse = warehouse;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
