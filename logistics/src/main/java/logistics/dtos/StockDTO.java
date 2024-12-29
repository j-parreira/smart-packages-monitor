package logistics.dtos;

import logistics.entities.Stock;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class StockDTO implements Serializable {
    private Long id;
    private ProductDTO product;
    private WarehouseDTO warehouse;
    private Long quantity;

    public StockDTO() {
    }

    public StockDTO(Long id, ProductDTO product, WarehouseDTO warehouse, Long quantity) {
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

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public WarehouseDTO getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseDTO warehouse) {
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
                ProductDTO.fromEntity(stock.getProduct()),
                WarehouseDTO.fromEntity(stock.getWarehouse()),
                stock.getQuantity()
        );
    }

    public static Stock toEntity(StockDTO stockDTO) {
        Stock stock = new Stock(
                ProductDTO.toEntity(stockDTO.getProduct()),
                WarehouseDTO.toEntity(stockDTO.getWarehouse()),
                stockDTO.getQuantity()
        );
        stock.setId(stockDTO.getId());
        return stock;
    }

    public static List<StockDTO> fromEntity(List<Stock> stocks) {
        return stocks.stream().map(StockDTO::fromEntity).collect(Collectors.toList());
    }

    public static List<Stock> toEntity(List<StockDTO> stockDTOs) {
        return stockDTOs.stream().map(StockDTO::toEntity).collect(Collectors.toList());
    }
}
