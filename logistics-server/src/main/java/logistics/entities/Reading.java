package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    private Sensor sensor;

    @NotNull
    private double valueOne;

    private double valueTwo;

    @NotNull
    @CreationTimestamp
    private LocalDateTime timestamp;

    public Reading() {
    }

    public Reading(Sensor sensor, double valueOne) {
        this.sensor = sensor;
        this.valueOne = valueOne;
        this.timestamp = LocalDateTime.now();
    }

    public Reading(Sensor sensor, double valueOne, double valueTwo) {
        this.sensor = sensor;
        this.valueOne = valueOne;
        this.valueTwo = valueTwo;
        this.timestamp = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
