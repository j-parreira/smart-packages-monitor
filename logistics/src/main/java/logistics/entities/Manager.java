package logistics.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllManagers",
                query = "SELECT m FROM Manager m ORDER BY m.name"
        ),
        @NamedQuery(
                name = "getManagerByEmail",
                query = "SELECT m FROM Manager m WHERE m.email = :email"
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Manager manager = (Manager) o;
        return Objects.equals(office, manager.office);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(office);
        return result;
    }
}
