package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllReadings",
                query = "SELECT r FROM Reading r ORDER BY r.timestamp"
        ),
        @NamedQuery(
                name = "getReadingsBySensor",
                query = "SELECT r FROM Reading r WHERE r.sensor = :sensor ORDER BY r.timestamp"
        )
})
@Table(name = "readings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sensor_id", "timestamp"})
})
public class Reading extends Versionable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Sensor sensor;

    @NotNull
    @Column(name = "value_one")
    private double valueOne;

    @Column(name = "value_two")
    private double valueTwo;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp")
    private Date timestamp;

    public Reading() {
    }

    public Reading(Sensor sensor, double valueOne, double valueTwo) {
        this.sensor = sensor;
        this.valueOne = valueOne;
        this.valueTwo = valueTwo;
        this.timestamp = new Date();
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

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Reading reading = (Reading) o;
        return Double.compare(valueOne, reading.valueOne) == 0 && Double.compare(valueTwo, reading.valueTwo) == 0 && Objects.equals(id, reading.id) && Objects.equals(sensor, reading.sensor) && Objects.equals(timestamp, reading.timestamp);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(sensor);
        result = 31 * result + Double.hashCode(valueOne);
        result = 31 * result + Double.hashCode(valueTwo);
        result = 31 * result + Objects.hashCode(timestamp);
        return result;
    }
}
