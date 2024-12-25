package logistics.dtos;

import logistics.entities.Order;
import logistics.entities.Stock;
import logistics.entities.Volume;

import java.util.List;

public class ProductDTO {
    private Long id;
    private String name;
    private String type;
    private List<Order> orders;
    private List<Volume> volumes;
    private List<Stock> stocks;

}
