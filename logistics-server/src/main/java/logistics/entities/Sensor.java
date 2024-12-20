package logistics.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import logistics.enums.SensorType;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Sensor {
    @Id
    private int id;
    @NotNull
    private SensorType type;
    @NotNull
    private boolean isActive;
    private float threshold;
    @OneToMany
    private List<Reading> readings;


    public Sensor() {
    }

    public Sensor(int id, SensorType type, boolean isActive) {
        this.id = id;
        this.type = type;
        this.isActive = isActive;
        this.readings = new LinkedList<>();
    }

    public Sensor(int id, SensorType type, boolean isActive, float threshold) {
        this.id = id;
        this.type = type;
        this.isActive = isActive;
        this.threshold = threshold;
        this.readings = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull SensorType getType() {
        return type;
    }

    public void setType(@NotNull SensorType type) {
        this.type = type;
    }

    @NotNull
    public boolean isActive() {
        return isActive;
    }

    public void setActive(@NotNull boolean active) {
        isActive = active;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public List<Reading> getReadings() {
        return readings;
    }

    public void setReadings(List<Reading> readings) {
        this.readings = readings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sensor sensor = (Sensor) o;
        return id == sensor.id && isActive == sensor.isActive && type.equals(sensor.type);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + type.hashCode();
        return result;
    }
}
