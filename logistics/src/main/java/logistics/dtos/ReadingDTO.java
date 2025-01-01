package logistics.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import logistics.entities.Reading;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReadingDTO implements Serializable {
    private Long id;
    private Long sensorId;
    private double valueOne;
    private double valueTwo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date timestamp;

    public ReadingDTO() {
    }

    public ReadingDTO(Long id, Long sensorId, double valueOne, double valueTwo, Date timestamp) {
        this.id = id;
        this.sensorId = sensorId;
        this.valueOne = valueOne;
        this.valueTwo = valueTwo;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public double getValueOne() {
        return valueOne;
    }

    public void setValueOne(double valueOne) {
        this.valueOne = valueOne;
    }

    public double getValueTwo() {
        return valueTwo;
    }

    public void setValueTwo(double valueTwo) {
        this.valueTwo = valueTwo;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public static ReadingDTO fromEntity(Reading reading) {
        return new ReadingDTO(
                reading.getId(),
                reading.getSensor().getId(),
                reading.getValueOne(),
                reading.getValueTwo(),
                reading.getTimestamp()
        );
    }

    public static List<ReadingDTO> fromEntity(List<Reading> readings) {
        return readings.stream().map(ReadingDTO::fromEntity).collect(Collectors.toList());
    }
}
