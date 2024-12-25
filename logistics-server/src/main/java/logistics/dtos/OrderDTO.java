package logistics.dtos;

import java.util.List;

public class OrderDTO {

    private Long id;
    private Long Customer;
    private List<Long> products;
    private List<Long> volumes;
    private String status;
    private String createdAt;
    private String paymentType;

    public OrderDTO() {
    }

    public OrderDTO(Long id, Long Customer, List<Long> products, List<Long> volumes, String status, String createdAt, String paymentType) {
        this.id = id;
        this.Customer = Customer;
        this.products = products;
        this.volumes = volumes;
        this.status = status;
        this.createdAt = createdAt;
        this.paymentType = paymentType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomer() {
        return Customer;
    }

    public void setCustomer(Long customer) {
        Customer = customer;
    }

    public List<Long> getProducts() {
        return products;
    }

    public void setProducts(List<Long> products) {
        this.products = products;
    }

    public List<Long> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Long> volumes) {
        this.volumes = volumes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
