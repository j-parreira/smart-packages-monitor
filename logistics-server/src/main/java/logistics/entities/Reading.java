package logistics.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
public class Reading {
    @Id
    private int id;
    @NotNull
    private String value;
    @NotNull
    private Date timestamp;

    public Reading() {
    }

    public Reading(int id, String value, Date timestamp) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
    }
}
