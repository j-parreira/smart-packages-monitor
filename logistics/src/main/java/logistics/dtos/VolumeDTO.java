package logistics.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String volumeCode;
    private Long productId;
    private List<SensorDTO> sensors;
    private Long dispatchedByEmployeeId;
    private VolumeStatus status;
    private Long orderId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date dispatchedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date updatedAt;

    public VolumeDTO() {
        this.sensors = new LinkedList<>();
    }

    public VolumeDTO(VolumeType type, String volumeCode, Long productId, Long dispatchedByEmployeeId, VolumeStatus status, Long orderId, Date dispatchedAt, Date updatedAt) {
        this.type = type;
        this.volumeCode = volumeCode;
        this.productId = productId;
        this.sensors = new LinkedList<>();
        this.dispatchedByEmployeeId = dispatchedByEmployeeId;
        this.status = status;
        this.orderId = orderId;
        this.dispatchedAt = dispatchedAt;
        this.updatedAt = updatedAt;
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static VolumeDTO fromEntity(Volume volume) {
        return new VolumeDTO(volume.getType(), volume.getVolumeCode(), volume.getProduct().getId(), volume.getDispatchedBy().getId(), volume.getStatus(), volume.getOrder().getId(), volume.getDispatchedAt(), volume.getUpdatedAt());
    }

    public static List<VolumeDTO> fromEntity(List<Volume> volumes) {
        return volumes.stream().map(VolumeDTO::fromEntity).collect(Collectors.toList());
    }
}


