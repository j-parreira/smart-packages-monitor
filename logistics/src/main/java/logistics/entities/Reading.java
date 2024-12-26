package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Reading.getBySensor",
                query = "SELECT r FROM Reading r WHERE r.sensor = : sensor ORDER BY r.timestamp"
        )
})
@Table(name = "readings")
public class Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @ManyToOne
    private Sensor sensor;

    @NotBlank
    private double valueOne;

    private double valueTwo;

    @NotBlank
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

    public Long getId() {
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
