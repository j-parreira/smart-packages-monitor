package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllEmployees",
                query = "SELECT e FROM Employee e ORDER BY e.name"
        )
})
public class Employee extends Person {
    @NotBlank
    @ManyToOne
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
