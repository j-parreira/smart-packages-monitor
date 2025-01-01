package logistics.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import logistics.entities.Order;
import logistics.enums.OrderStatus;
import logistics.enums.PaymentType;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class OrderDTO implements Serializable {
    private Long id;
    private Long customerId;
    private List<ProductDTO> products;
    private List<VolumeDTO> volumes;
    private OrderStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdAt;
    private PaymentType paymentType;

    public OrderDTO() {
        this.products = new LinkedList<>();
        this.volumes = new LinkedList<>();
    }

    public OrderDTO(Long id, Long customerId, OrderStatus status, Date createdAt, PaymentType paymentType) {
        this.id = id;
        this.customerId = customerId;
        this.products = new LinkedList<>();
        this.volumes = new LinkedList<>();
        this.status = status;
        this.createdAt = createdAt;
        this.paymentType = paymentType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public List<VolumeDTO> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<VolumeDTO> volumes) {
        this.volumes = volumes;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public static OrderDTO fromEntity(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getCustomer().getId(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getPaymentType()
        );
    }

    public static List<OrderDTO> fromEntity(List<Order> orders) {
        return orders.stream().map(OrderDTO::fromEntity).collect(Collectors.toList());
    }
}
