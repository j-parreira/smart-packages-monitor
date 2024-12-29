package logistics.dtos;

import logistics.entities.Product;
import logistics.enums.ProductType;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO implements Serializable {
    private Long id;
    private String name;
    private ProductType type;
    private List<OrderDTO> orders;
    private List<VolumeDTO> volumes;
    private List<StockDTO> stocks;

    public ProductDTO() {
        this.orders = new LinkedList<>();
        this.volumes = new LinkedList<>();
        this.stocks = new LinkedList<>();
    }

    public ProductDTO(Long id, String name, ProductType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.orders = new LinkedList<>();
        this.volumes = new LinkedList<>();
        this.stocks = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public List<VolumeDTO> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<VolumeDTO> volumes) {
        this.volumes = volumes;
    }

    public List<StockDTO> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockDTO> stocks) {
        this.stocks = stocks;
    }

    public static ProductDTO fromEntity(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getType()
        );
    }

    public static Product toEntity(ProductDTO productDTO) {
        Product product = new Product(
                productDTO.getName(),
                productDTO.getType()
        );
        product.setId(productDTO.getId());
        return product;
    }

    public static List<ProductDTO> fromEntity(List<Product> products) {
        return products.stream().map(ProductDTO::fromEntity).collect(Collectors.toList());
    }

    public static List<Product> toEntity(List<ProductDTO> productDTOs) {
        return productDTOs.stream().map(ProductDTO::toEntity).collect(Collectors.toList());
    }

}
