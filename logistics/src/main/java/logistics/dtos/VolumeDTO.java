package logistics.dtos;

import logistics.entities.Volume;
import logistics.enums.VolumeStatus;
import logistics.enums.VolumeType;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class VolumeDTO implements Serializable {
    private Long id;
    private VolumeType type;
    private long volumeNumber;
    private Long productId;
    private List<SensorDTO> sensors;
    private Long dispatchedByEmployeeId;
    private VolumeStatus status;
    private Long orderId;
    private Date dispatchedAt;
    private Date arrivedAt;

    public VolumeDTO() {
        this.sensors = new LinkedList<>();
    }

    public VolumeDTO(VolumeType type, long volumeNumber, Long productId, Long dispatchedByEmployeeId, VolumeStatus status, Long orderId, Date dispatchedAt, Date arrivedAt) {
        this.type = type;
        this.volumeNumber = volumeNumber;
        this.productId = productId;
        this.sensors = new LinkedList<>();
        this.dispatchedByEmployeeId = dispatchedByEmployeeId;
        this.status = status;
        this.orderId = orderId;
        this.dispatchedAt = dispatchedAt;
        this.arrivedAt = arrivedAt;
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

    public long getVolumeNumber() {
        return volumeNumber;
    }

    public void setVolumeNumber(long volumeNumber) {
        this.volumeNumber = volumeNumber;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<SensorDTO> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorDTO> sensors) {
        this.sensors = sensors;
    }

    public Long getDispatchedByEmployeeId() {
        return dispatchedByEmployeeId;
    }

    public void setDispatchedByEmployeeId(Long dispatchedByEmployeeId) {
        this.dispatchedByEmployeeId = dispatchedByEmployeeId;
    }

    public VolumeStatus getStatus() {
        return status;
    }

    public void setStatus(VolumeStatus status) {
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getDispatchedAt() {
        return dispatchedAt;
    }

    public void setDispatchedAt(Date dispatchedAt) {
        this.dispatchedAt = dispatchedAt;
    }

    public Date getArrivedAt() {
        return arrivedAt;
    }

    public void setArrivedAt(Date arrivedAt) {
        this.arrivedAt = arrivedAt;
    }

    public static VolumeDTO fromEntity(Volume volume) {
        return new VolumeDTO(
                volume.getType(),
                volume.getVolumeNumber(),
                volume.getProduct().getId(),
                volume.getDispatchedBy().getId(),
                volume.getStatus(),
                volume.getOrder().getId(),
                volume.getDispatchedAt(),
                volume.getArrivedAt()
        );
    }

    public static List<VolumeDTO> fromEntity(List<Volume> volumes) {
        return volumes.stream().map(VolumeDTO::fromEntity).collect(Collectors.toList());
    }
}


