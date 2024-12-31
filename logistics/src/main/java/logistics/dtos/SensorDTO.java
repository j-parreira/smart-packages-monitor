package logistics.dtos;

import logistics.entities.Sensor;
import logistics.enums.SensorType;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SensorDTO implements Serializable {
    private Long id;
    private Long volumeId;
    private SensorType type;
    private boolean isActive;
    private float maxThreshold;
    private float minThreshold;
    private long timeInterval;
    private List<ReadingDTO> readings;

    public SensorDTO() {
        this.readings = new LinkedList<>();
    }

    public SensorDTO(Long id, Long volumeId, SensorType type, boolean isActive, float maxThreshold, float minThreshold, long timeInterval) {
        this.id = id;
        this.volumeId = volumeId;
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

    public Long getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(Long volumeId) {
        this.volumeId = volumeId;
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

    public long getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(long timeInterval) {
        this.timeInterval = timeInterval;
    }

    public List<ReadingDTO> getReadings() {
        return readings;
    }

    public void setReadings(List<ReadingDTO> readings) {
        this.readings = readings;
    }

    public static SensorDTO fromEntity(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getVolume().getId(),
                sensor.getType(),
                sensor.isActive(),
                sensor.getMaxThreshold(),
                sensor.getMinThreshold(),
                sensor.getTimeInterval());
    }

    public static List<SensorDTO> fromEntity(List<Sensor> sensors) {
        return sensors.stream().map(SensorDTO::fromEntity).collect(Collectors.toList());
    }
}
