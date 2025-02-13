package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import logistics.enums.OrderStatus;
import logistics.enums.PaymentType;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o ORDER BY o.createdAt"
        )
})
@Table(name = "orders", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customer_id", "created_at"})
})
public class Order extends Versionable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    Customer customer;

    @NotNull
    @ManyToMany(mappedBy = "orders")
    List<Product> products;

    @OneToMany(mappedBy = "order")
    List<Volume> volumes;

    @NotNull
    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    PaymentType paymentType;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP(0)")
    private Date createdAt;

    public Order() {
        this.products = new LinkedList<>();
        this.volumes = new LinkedList<>();
    }

    public Order(Customer customer, List<Product> products, PaymentType paymentType) {
        this.customer = customer;
        this.products = products;
        this.volumes = new LinkedList<>();
        this.status = OrderStatus.PROCESSING;
        this.createdAt = new Date();
        this.paymentType = paymentType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(customer, order.customer) && Objects.equals(products, order.products) && Objects.equals(volumes, order.volumes) && status == order.status && Objects.equals(createdAt, order.createdAt) && paymentType == order.paymentType;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(customer);
        result = 31 * result + Objects.hashCode(products);
        result = 31 * result + Objects.hashCode(volumes);
        result = 31 * result + Objects.hashCode(status);
        result = 31 * result + Objects.hashCode(createdAt);
        result = 31 * result + Objects.hashCode(paymentType);
        return result;
    }
}
