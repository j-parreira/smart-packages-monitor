package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import logistics.enums.OrderStatus;
import logistics.enums.VolumeType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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
    @ManyToMany
    private List<Product> products;

    @NotNull
    @OneToMany
    private List<Sensor> sensors;

    @NotNull
    @ManyToOne
    private Employee dispatchedBy;

    @NotNull
    private OrderStatus status;

    @NotNull
    @ManyToOne
    private Order order;

    @NotNull
    @CreationTimestamp
    private LocalDateTime dispatchedAt;

    @CreationTimestamp
    private LocalDateTime arrivedAt;

    public Volume() {
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

    public long getId() {
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
}
