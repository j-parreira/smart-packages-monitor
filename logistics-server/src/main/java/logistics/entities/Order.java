package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import logistics.enums.OrderStatus;
import logistics.enums.PaymentType;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    Customer customer;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<Product> products;
    OrderStatus status;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    PaymentType paymentType;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<Volume> volumes;

    public Order() {
    }

    public Order(Customer customer, List<Product> products, PaymentType paymentType) {
        this.customer = customer;
        this.products = products;
        this.status = OrderStatus.PROCESSING;
        this.paymentType = paymentType;
        this.volumes = new LinkedList<>();
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

}
