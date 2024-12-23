package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import logistics.enums.ProductType;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    @ManyToOne
    Warehouse warehouse;
    @NotNull
    ProductType type;
    @ManyToOne
    Order order;
    @ManyToOne
    Volume volume;

    public Product() {
    }

    public Product(String name, Warehouse warehouse, ProductType type) {
        this.name = name;
        this.warehouse = warehouse;
        this.type = type;
        this.order = null;
        this.volume = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }
}
