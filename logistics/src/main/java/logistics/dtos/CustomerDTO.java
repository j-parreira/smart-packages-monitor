package logistics.dtos;

import logistics.entities.Customer;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerDTO implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private List<OrderDTO> orders;

    public CustomerDTO() {
        this.orders = new LinkedList<>();
    }

    public CustomerDTO(Long id, String name, String email, String password, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.orders = new LinkedList<>();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public static CustomerDTO fromEntity(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                null,
                customer.getAddress()
        );
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
        Customer customer =  new Customer(
                customerDTO.getName(),
                customerDTO.getEmail(),
                customerDTO.getPassword(),
                customerDTO.getAddress()
        );
        customer.setId(customerDTO.getId());
        return customer;
    }

    public static List<CustomerDTO> fromEntity(List<Customer> customers) {
        return customers.stream().map(CustomerDTO::fromEntity).collect(Collectors.toList());
    }

    public static List<Customer> toEntity(List<CustomerDTO> customerDTOs) {
        return customerDTOs.stream().map(CustomerDTO::toEntity).collect(Collectors.toList());
    }
}
