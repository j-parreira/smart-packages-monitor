package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import logistics.enums.OrderStatus;
import logistics.enums.PaymentType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    Customer customer;

    @NotNull
    @ManyToMany
    List<Product> products;

    @OneToMany(mappedBy = "order")
    List<Volume> volumes;

    @NotNull
    OrderStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @NotNull
    PaymentType paymentType;


    public Order() {
    }

    public Order(Customer customer, List<Product> products, PaymentType paymentType) {
        this.customer = customer;
        this.products = products;
        this.volumes = new LinkedList<>();
        this.status = OrderStatus.PROCESSING;
        this.createdAt = LocalDateTime.now();
        this.paymentType = paymentType;
    }

    public long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
