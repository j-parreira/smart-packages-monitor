package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import logistics.enums.OrderStatus;
import logistics.enums.PaymentType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o ORDER BY o.createdAt"
        )
})
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    Customer customer;

    @NotNull
    @ManyToMany
    List<Product> products;

    @OneToMany(mappedBy = "order")
    List<Volume> volumes;

    @NotBlank
    OrderStatus status;

    @CreationTimestamp
    @NotBlank
    private LocalDateTime createdAt;

    @NotBlank
    PaymentType paymentType;

    public Order() {
        this.products = new LinkedList<>();
        this.volumes = new LinkedList<>();
    }

    public Order(Customer customer, List<Product> products, PaymentType paymentType) {
        this.customer = customer;
        this.products = products;
        this.volumes = new LinkedList<>();
        this.status = OrderStatus.PROCESSING;
        this.createdAt = LocalDateTime.now();
        this.paymentType = paymentType;
    }

    public Long getId() {
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

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public void addVolume(Volume volume) {
        this.volumes.add(volume);
    }

    public void removeVolume(Volume volume) {
        this.volumes.remove(volume);
    }
}
