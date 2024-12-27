package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import logistics.enums.OrderStatus;
import logistics.enums.VolumeType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(
        name = "getAllVolumesInOrder",
        query = "SELECT v FROM Volume v WHERE v.order = :order"
)
@Table(name = "volumes")
public class Volume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private VolumeType type;

    @NotNull
    @Column(name = "volume_number")
    private int volumeNumber;

    @NotNull
    @ManyToMany(mappedBy = "volumes")
    private List<Product> products;

    @NotNull
    @OneToMany
    private List<Sensor> sensors;

    @NotNull
    @ManyToOne
    @Column(name = "dispatched_by")
    private Employee dispatchedBy;

    @NotNull
    private OrderStatus status;

    @NotNull
    @ManyToOne
    private Order order;

    @NotNull
    @CreationTimestamp
    @Column(name = "dispatched_at")
    private LocalDateTime dispatchedAt;

    @CreationTimestamp
    @Column(name = "arrived_at")
    private LocalDateTime arrivedAt;

    public Volume() {
        this.products = new LinkedList<>();
        this.sensors = new LinkedList<>();
    }

    public Volume(VolumeType type, int volumeNumber, List<Product> products, List<Sensor> sensors, Employee dispatchedBy, OrderStatus status, Order order) {
        this.type = type;
        this.volumeNumber = volumeNumber;
        this.products = products;
        this.sensors = sensors;
        this.dispatchedBy = dispatchedBy;
        this.status = status;
        this.order = order;
        this.dispatchedAt = LocalDateTime.now();
        this.arrivedAt = null;
    }

    public Long getId() {
        return id;
    }

    public VolumeType getType() {
        return type;
    }

    public void setType(VolumeType type) {
        this.type = type;
    }

    public int getVolumeNumber() {
        return volumeNumber;
    }

    public void setVolumeNumber(int volumeNumber) {
        this.volumeNumber = volumeNumber;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public Employee getDispatchedBy() {
        return dispatchedBy;
    }

    public void setDispatchedBy(Employee dispatchedBy) {
        this.dispatchedBy = dispatchedBy;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDateTime getDispatchedAt() {
        return dispatchedAt;
    }

    public void setDispatchedAt(LocalDateTime dispatchedAt) {
        this.dispatchedAt = dispatchedAt;
    }

    public LocalDateTime getArrivedAt() {
        return arrivedAt;
    }

    public void setArrivedAt(LocalDateTime arrivedAt) {
        this.arrivedAt = arrivedAt;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public void addSensor(Sensor sensor) {
        this.sensors.add(sensor);
    }

    public void removeSensor(Sensor sensor) {
        this.sensors.remove(sensor);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Volume volume = (Volume) o;
        return volumeNumber == volume.volumeNumber && Objects.equals(id, volume.id) && type == volume.type && Objects.equals(products, volume.products) && Objects.equals(sensors, volume.sensors) && Objects.equals(dispatchedBy, volume.dispatchedBy) && status == volume.status && Objects.equals(order, volume.order) && Objects.equals(dispatchedAt, volume.dispatchedAt) && Objects.equals(arrivedAt, volume.arrivedAt);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(type);
        result = 31 * result + volumeNumber;
        result = 31 * result + Objects.hashCode(products);
        result = 31 * result + Objects.hashCode(sensors);
        result = 31 * result + Objects.hashCode(dispatchedBy);
        result = 31 * result + Objects.hashCode(status);
        result = 31 * result + Objects.hashCode(order);
        result = 31 * result + Objects.hashCode(dispatchedAt);
        result = 31 * result + Objects.hashCode(arrivedAt);
        return result;
    }
}
