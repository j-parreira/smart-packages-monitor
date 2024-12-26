package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import logistics.enums.SensorType;
import logistics.enums.VolumeType;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "sensors")
@NamedQueries({
        @NamedQuery(
                name = "getAllSensorsInVolume",
                query = "SELECT s FROM Sensor s WHERE s.volume.id = :volumeId"
        )
})
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @ManyToOne
    private Volume volume;

    @NotBlank
    private SensorType type;

    @NotBlank
    private boolean isActive;

    @NotBlank
    private long timeInterval;

    private float maxThreshold;

    private float minThreshold;

    @OneToMany(mappedBy = "sensor")
    private List<Reading> readings;

    public Sensor() {
    }

    public Sensor(SensorType type, boolean isActive, long timeInterval, float maxThreshold, float minThreshold) {
        this.type = type;
        this.isActive = isActive;
        this.timeInterval = timeInterval;
        this.maxThreshold = maxThreshold;
        this.minThreshold = minThreshold;
        this.readings = new LinkedList<>();
    }

    public Long getId() {
        return id;
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
}
