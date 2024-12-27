package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import logistics.enums.ProductType;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "SELECT p FROM Product p ORDER BY p.name"
        )
})
@Table(name = "products",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class Product extends Versionable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    ProductType type;

    @ManyToMany
    @JoinTable(
            name = "product_order",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id")
    )
    List<Order> orders;

    @ManyToMany
    @JoinTable(
            name = "product_volume",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "volume_id", referencedColumnName = "id")
    )
    List<Volume> volumes;

    @OneToMany(mappedBy = "product")
    List<Stock> stocks;

    public Product() {
        this.orders = new LinkedList<>();
        this.volumes = new LinkedList<>();
        this.stocks = new LinkedList<>();
    }

    public Product(String name, ProductType type) {
        this.name = name;
        this.type = type;
        this.orders = new LinkedList<>();
        this.volumes = new LinkedList<>();
        this.stocks = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void removeOrder(Order order) {
        this.orders.remove(order);
    }

    public void addVolume(Volume volume) {
        this.volumes.add(volume);
    }

    public void removeVolume(Volume volume) {
        this.volumes.remove(volume);
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public void removeStock(Stock stock) {
        this.stocks.remove(stock);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && type == product.type && Objects.equals(orders, product.orders) && Objects.equals(volumes, product.volumes) && Objects.equals(stocks, product.stocks);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(type);
        result = 31 * result + Objects.hashCode(orders);
        result = 31 * result + Objects.hashCode(volumes);
        result = 31 * result + Objects.hashCode(stocks);
        return result;
    }
}
