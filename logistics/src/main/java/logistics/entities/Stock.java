package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getStocksByProduct",
                query = "SELECT s FROM Stock s WHERE s.product.id = :productId"
        )
})
@Table(name = "stocks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"product_id", "warehouse_id"})
        })
public class Stock extends Versionable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Product product;

    @NotNull
    @ManyToOne
    private Warehouse warehouse;

    @NotBlank
    private Long quantity;

    public Stock() {
    }

    public Stock(Product product, Warehouse warehouse, Long quantity) {
        this.product = product;
        this.warehouse = warehouse;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(Long quantity) {
        this.quantity += quantity;
    }

    public void removeQuantity(Long quantity) {
        this.quantity -= quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;
        return Objects.equals(id, stock.id) && Objects.equals(product, stock.product) && Objects.equals(warehouse, stock.warehouse) && Objects.equals(quantity, stock.quantity);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(product);
        result = 31 * result + Objects.hashCode(warehouse);
        result = 31 * result + Objects.hashCode(quantity);
        return result;
    }
}
