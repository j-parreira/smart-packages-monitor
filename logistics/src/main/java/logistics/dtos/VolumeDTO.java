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
    private List<ProductDTO> products;
    private List<SensorDTO> sensors;
    private EmployeeDTO dispatchedBy;
    private VolumeStatus status;
    private OrderDTO order;
    private Date dispatchedAt;
    private Date arrivedAt;

    public VolumeDTO() {
        this.products = new LinkedList<>();
        this.sensors = new LinkedList<>();
    }

    public VolumeDTO(VolumeType type, long volumeNumber, EmployeeDTO dispatchedBy, VolumeStatus status, OrderDTO order, Date dispatchedAt, Date arrivedAt) {
        this.type = type;
        this.volumeNumber = volumeNumber;
        this.products = new LinkedList<>();
        this.sensors = new LinkedList<>();
        this.dispatchedBy = dispatchedBy;
        this.status = status;
        this.order = order;
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

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public List<SensorDTO> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorDTO> sensors) {
        this.sensors = sensors;
    }

    public EmployeeDTO getDispatchedBy() {
        return dispatchedBy;
    }

    public void setDispatchedBy(EmployeeDTO dispatchedBy) {
        this.dispatchedBy = dispatchedBy;
    }

    public VolumeStatus getStatus() {
        return status;
    }

    public void setStatus(VolumeStatus status) {
        this.status = status;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
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
                EmployeeDTO.fromEntity(volume.getDispatchedBy()),
                volume.getStatus(),
                OrderDTO.fromEntity(volume.getOrder()),
                volume.getDispatchedAt(),
                volume.getArrivedAt()
        );
    }

    public static Volume toEntity(VolumeDTO volumeDTO) {
        Volume volume = new Volume(
                volumeDTO.getType(),
                volumeDTO.getVolumeNumber(),
                ProductDTO.toEntity(volumeDTO.getProducts()),
                SensorDTO.toEntity(volumeDTO.getSensors()),
                EmployeeDTO.toEntity(volumeDTO.getDispatchedBy()),
                volumeDTO.getStatus(),
                OrderDTO.toEntity(volumeDTO.getOrder())
        );
        volume.setId(volumeDTO.getId());
        volume.setDispatchedAt(volumeDTO.getDispatchedAt());
        volume.setArrivedAt(volumeDTO.getArrivedAt());
        return volume;
    }

    public static List<VolumeDTO> fromEntity(List<Volume> volumes) {
        return volumes.stream().map(VolumeDTO::fromEntity).collect(Collectors.toList());
    }

    public static List<Volume> toEntity(List<VolumeDTO> volumeDTOs) {
        return volumeDTOs.stream().map(VolumeDTO::toEntity).collect(Collectors.toList());
    }
}


