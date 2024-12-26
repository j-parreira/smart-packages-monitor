package logistics.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotNull;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllManagers",
                query = "SELECT m FROM Manager m ORDER BY m.name"
        )
})
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
