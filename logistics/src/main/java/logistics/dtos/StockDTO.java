package logistics.dtos;

import jakarta.validation.constraints.NotNull;
import logistics.entities.Product;
import logistics.entities.Stock;
import logistics.entities.Warehouse;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class StockDTO implements Serializable {
    private Long id;
    private Product product;
    private Warehouse warehouse;
    private Long quantity;

    public StockDTO() {
    }

    public StockDTO(Long id, Product product, Warehouse warehouse, Long quantity) {
        this.id = id;
        this.product = product;
        this.warehouse = warehouse;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public static StockDTO fromEntity(Stock stock) {
        return new StockDTO(
                stock.getId(),
                stock.getProduct(),
                stock.getWarehouse(),
                stock.getQuantity()
        );
    }

    public static List<StockDTO> fromEntity(List<Stock> stocks) {
        return stocks.stream().map(StockDTO::fromEntity).collect(Collectors.toList());
    }
}
