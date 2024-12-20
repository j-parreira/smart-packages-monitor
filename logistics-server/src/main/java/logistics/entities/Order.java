package logistics.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import logistics.enums.OrderStatus;
import logistics.enums.PaymentType;

import java.util.Date;
import java.util.List;

@Entity
public class Order {
    @Id
    private long id;
    @NotNull
    private Customer customer;

    private List<Volume> volumes;
    private List<Product> products;
    private OrderStatus status;

    private Date createdAt;
    private PaymentType paymentType;
    private String address;



    public Order() {
    }
}
