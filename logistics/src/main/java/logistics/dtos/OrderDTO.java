package logistics.dtos;

import jakarta.validation.constraints.NotNull;
import logistics.entities.Customer;
import logistics.entities.Order;
import logistics.entities.Product;
import logistics.entities.Volume;
import logistics.enums.OrderStatus;
import logistics.enums.PaymentType;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO implements Serializable {
    @NotNull
    private Long id;

    @NotNull
    private Customer Customer;

    @NotNull
    private List<Product> products;

    private List<Volume> volumes;

    @NotNull
    private OrderStatus status;

    @NotNull
    private Date createdAt;

    @NotNull
    private PaymentType paymentType;

    public OrderDTO() {
        this.products = new LinkedList<>();
        this.volumes = new LinkedList<>();
    }

    public OrderDTO(Long id, Customer Customer, OrderStatus status, Date createdAt, PaymentType paymentType) {
        this.id = id;
        this.Customer = Customer;
        this.products = new LinkedList<>();
        this.volumes = new LinkedList<>();
        this.status = status;
        this.createdAt = createdAt;
        this.paymentType = paymentType;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return Customer;
    }

    public void setCustomer(Customer customer) {
        Customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
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
                order.getCustomer(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getPaymentType()
        );
    }

    public static List<OrderDTO> fromEntity(List<Order> orders) {
        return orders.stream().map(OrderDTO::fromEntity).collect(Collectors.toList());
    }
}
