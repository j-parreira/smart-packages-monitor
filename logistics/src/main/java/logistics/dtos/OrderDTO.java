package logistics.dtos;

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
    private CustomerDTO Customer;
    private List<ProductDTO> products;
    private List<VolumeDTO> volumes;
    private OrderStatus status;
    private Date createdAt;
    private PaymentType paymentType;

    public OrderDTO() {
        this.products = new LinkedList<>();
        this.volumes = new LinkedList<>();
    }

    public OrderDTO(Long id, CustomerDTO customer, List<ProductDTO> products, OrderStatus status, Date createdAt, PaymentType paymentType) {
        this.id = id;
        this.Customer = customer;
        this.products = products;
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

    public CustomerDTO getCustomer() {
        return Customer;
    }

    public void setCustomer(CustomerDTO customer) {
        Customer = customer;
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
                CustomerDTO.fromEntity(order.getCustomer()),
                ProductDTO.fromEntity(order.getProducts()),
                order.getStatus(),
                order.getCreatedAt(),
                order.getPaymentType()
        );
    }

    public static Order toEntity(OrderDTO orderDTO) {
        Order order = new Order(
                CustomerDTO.toEntity(orderDTO.getCustomer()),
                ProductDTO.toEntity(orderDTO.getProducts()),
                orderDTO.getPaymentType()
        );
        order.setId(orderDTO.getId());
        order.setStatus(orderDTO.getStatus());
        order.setCreatedAt(orderDTO.getCreatedAt());
        return order;
    }

    public static List<OrderDTO> fromEntity(List<Order> orders) {
        return orders.stream().map(OrderDTO::fromEntity).collect(Collectors.toList());
    }

    public static List<Order> toEntity(List<OrderDTO> ordersDTO) {
        return ordersDTO.stream().map(OrderDTO::toEntity).collect(Collectors.toList());
    }
}
