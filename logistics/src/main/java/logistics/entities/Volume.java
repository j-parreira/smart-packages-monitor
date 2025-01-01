package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import logistics.enums.VolumeStatus;
import logistics.enums.VolumeType;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllVolumes",
                query = "SELECT v FROM Volume v"
        ),
        @NamedQuery(
                name = "getVolumeByVolumeCode",
                query = "SELECT v FROM Volume v WHERE v.volumeCode = :volumeCode"
        )
})
@Table(name = "volumes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"volume_code"})
})
public class Volume extends Versionable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private VolumeType type;

    @NotNull
    @Column(name = "volume_code", unique = true)
    private String volumeCode;

    @NotNull
    @ManyToOne
    private Product product;

    @NotNull
    @OneToMany(mappedBy = "volume")
    private List<Sensor> sensors;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "dispatched_by", nullable = false)
    private Employee dispatchedBy;

    @NotNull
    @Enumerated(EnumType.STRING)
    private VolumeStatus status;

    @NotNull
    @ManyToOne
    private Order order;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dispatched_at", columnDefinition = "TIMESTAMP(0)")
    private Date dispatchedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP(0)")
    private Date updatedAt;

    public Volume() {
        this.sensors = new LinkedList<>();
    }

    public Volume(VolumeType type, Product product, List<Sensor> sensors, Employee dispatchedBy, VolumeStatus status, Order order) {
        this.type = type;
        this.volumeCode = "T" + type.toString() + "P" + product.getId().toString() + "O" + order.getId().toString() + "E" + dispatchedBy.getId().toString() + "D" + new Date().getTime();
        this.product = product;
        this.sensors = sensors;
        this.dispatchedBy = dispatchedBy;
        this.status = status;
        this.order = order;
        this.dispatchedAt = new Date();
        this.updatedAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VolumeType getType() {
        return type;
    }

    public void setType(VolumeType type) {
        this.type = type;
    }

    public String getVolumeCode() {
        return volumeCode;
    }

    public void setVolumeCode(String volumeCode) {
        this.volumeCode = volumeCode;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public VolumeStatus getStatus() {
        return status;
    }

    public void setStatus(VolumeStatus status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Date getDispatchedAt() {
        return dispatchedAt;
    }

    public void setDispatchedAt(Date dispatchedAt) {
        this.dispatchedAt = dispatchedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date arrivedAt) {
        this.updatedAt = arrivedAt;
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
        return Objects.equals(id, volume.id) && type == volume.type && Objects.equals(volumeCode, volume.volumeCode) && Objects.equals(product, volume.product) && Objects.equals(sensors, volume.sensors) && Objects.equals(dispatchedBy, volume.dispatchedBy) && status == volume.status && Objects.equals(order, volume.order) && Objects.equals(dispatchedAt, volume.dispatchedAt) && Objects.equals(updatedAt, volume.updatedAt);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(type);
        result = 31 * result + Objects.hashCode(volumeCode);
        result = 31 * result + Objects.hashCode(product);
        result = 31 * result + Objects.hashCode(sensors);
        result = 31 * result + Objects.hashCode(dispatchedBy);
        result = 31 * result + Objects.hashCode(status);
        result = 31 * result + Objects.hashCode(order);
        result = 31 * result + Objects.hashCode(dispatchedAt);
        result = 31 * result + Objects.hashCode(updatedAt);
        return result;
    }
}
