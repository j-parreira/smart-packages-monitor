package logistics.dtos;

import logistics.entities.Stock;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class StockDTO implements Serializable {
    private Long id;
    private Long productId;
    private Long warehouseId;
    private Long quantity;

    public StockDTO() {
    }

    public StockDTO(Long id, Long productId, Long warehouseId, Long quantity) {
        this.id = id;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
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
                stock.getProduct().getId(),
                stock.getWarehouse().getId(),
                stock.getQuantity()
        );
    }

    public static List<StockDTO> fromEntity(List<Stock> stocks) {
        return stocks.stream().map(StockDTO::fromEntity).collect(Collectors.toList());
    }
}
