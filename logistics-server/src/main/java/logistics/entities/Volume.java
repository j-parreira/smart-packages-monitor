package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import logistics.enums.OrderStatus;
import logistics.enums.VolumeType;

import java.util.List;

@Entity
public class Volume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private VolumeType type;
    @NotNull
    private int volumeNumber;
    @NotNull
    @OneToMany(mappedBy = "volume")
    private List<Product> products;
    @NotNull
    @OneToMany
    private List<Sensor> sensors;
    @NotNull
    @ManyToOne
    private Employee dispatchedBy;
    @NotNull
    private OrderStatus status;
    @ManyToOne
    private Order order;

}
