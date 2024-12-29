package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import logistics.enums.SensorType;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllSensors",
                query = "SELECT s FROM Sensor s"
        ),
        @NamedQuery(
                name = "getAllSensorsInVolume",
                query = "SELECT s FROM Sensor s WHERE s.volume.id = :volumeId"
        )
})
@Table(name = "sensors", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"volume_id", "type"})
})
public class Sensor extends Versionable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Volume volume;

    @NotNull
    private SensorType type;

    @NotNull
    @Column(name = "is_active")
    private boolean isActive;

    @NotNull
    @Column(name = "time_interval")
    private long timeInterval;

    @Column(name = "max_threshold")
    private float maxThreshold;

    @Column(name = "min_threshold")
    private float minThreshold;

    @OneToMany(mappedBy = "sensor")
    private List<Reading> readings;

    public Sensor() {
        this.readings = new LinkedList<>();
    }

    public Sensor(SensorType type, boolean isActive, float maxThreshold, float minThreshold, long timeInterval) {
        this.volume = null;
        this.type = type;
        this.isActive = isActive;
        this.maxThreshold = maxThreshold;
        this.minThreshold = minThreshold;
        this.timeInterval = timeInterval;
        this.readings = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(long timeInterval) {
        this.timeInterval = timeInterval;
    }

    public float getMaxThreshold() {
        return maxThreshold;
    }

    public void setMaxThreshold(float maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public float getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(float minThreshold) {
        this.minThreshold = minThreshold;
    }

    public List<Reading> getReadings() {
        return readings;
    }

    public void setReadings(List<Reading> readings) {
        this.readings = readings;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Sensor sensor = (Sensor) o;
        return isActive == sensor.isActive && timeInterval == sensor.timeInterval && Float.compare(maxThreshold, sensor.maxThreshold) == 0 && Float.compare(minThreshold, sensor.minThreshold) == 0 && Objects.equals(id, sensor.id) && Objects.equals(volume, sensor.volume) && type == sensor.type && Objects.equals(readings, sensor.readings);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(volume);
        result = 31 * result + Objects.hashCode(type);
        result = 31 * result + Boolean.hashCode(isActive);
        result = 31 * result + Long.hashCode(timeInterval);
        result = 31 * result + Float.hashCode(maxThreshold);
        result = 31 * result + Float.hashCode(minThreshold);
        result = 31 * result + Objects.hashCode(readings);
        return result;
    }
}
