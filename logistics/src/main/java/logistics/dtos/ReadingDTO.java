package logistics.dtos;

import logistics.entities.Reading;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReadingDTO implements Serializable {
    private Long id;
    private SensorDTO sensor;
    private double valueOne;
    private double valueTwo;
    private Date timestamp;

    public ReadingDTO() {
    }

    public ReadingDTO(Long id, SensorDTO sensor, double valueOne, double valueTwo, Date timestamp) {
        this.id = id;
        this.sensor = sensor;
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

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
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
                SensorDTO.fromEntity(reading.getSensor()),
                reading.getValueOne(),
                reading.getValueTwo(),
                reading.getTimestamp()
        );
    }

    public static Reading toEntity(ReadingDTO readingDTO) {
        return new Reading(
                SensorDTO.toEntity(readingDTO.getSensor()),
                readingDTO.getValueOne(),
                readingDTO.getValueTwo()
        );
    }

    public static List<ReadingDTO> fromEntity(List<Reading> readings) {
        return readings.stream().map(ReadingDTO::fromEntity).collect(Collectors.toList());
    }

    public static List<Reading> toEntity(List<ReadingDTO> readings) {
        return readings.stream().map(ReadingDTO::toEntity).collect(Collectors.toList());
    }
}
