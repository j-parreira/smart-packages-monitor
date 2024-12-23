package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import logistics.enums.SensorType;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
