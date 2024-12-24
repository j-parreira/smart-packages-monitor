package logistics.entities;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class Manager extends Employee {
    @NotNull
    private String office;

    public Manager() {
    }

    public Manager(String name, String email, String password, Warehouse warehouse, String office) {
        super(name, email, password, warehouse);
        this.office = office;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
}
