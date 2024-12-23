package logistics.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import logistics.enums.SensorType;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Sensor {
    @Id
    private int id;
}
