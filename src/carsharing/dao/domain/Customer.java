package carsharing.dao.domain;

public class Customer {

    private Integer id;
    private String name;
    private Integer rentedCarId;

    public Customer(Integer id, String name, Integer rentedCarId) {
        this.id = id;
        this.name = name;
        this.rentedCarId = rentedCarId;
    }

    public Customer(Integer id, String name) {
        this(id, name,null);
    }

    public Customer(String name) {
        this(null, name, null);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getRentedCarId() {
        return rentedCarId;
    }

    @Override
    public String toString() {
        return null;
    }
}
