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
}
