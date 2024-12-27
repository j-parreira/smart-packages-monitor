package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllEmployees",
                query = "SELECT e FROM Employee e ORDER BY e.name"
        )
})
public class Employee extends Person {
    @NotNull
    @ManyToOne
    Warehouse warehouse;

    @OneToMany
    List<Volume> volumes;

    public Employee() {
        this.volumes = new LinkedList<>();
    }

    public Employee(String name, String email, String password, Warehouse warehouse) {
        super(name, email, password);
        this.warehouse = warehouse;
        this.volumes = new LinkedList<>();
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

    public void addVolume(Volume volume) {
        this.volumes.add(volume);
    }

    public void removeVolume(Volume volume) {
        this.volumes.remove(volume);
    }
}
