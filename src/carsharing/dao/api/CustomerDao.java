package carsharing.dao.api;

import carsharing.dao.domain.Customer;

import java.util.List;

public interface CustomerDao {

    List<Customer> getAllCustomers();
    void addCustomer(Customer c);
    void updateRentedCarId(Integer customerId, Integer rentedCarId);
}
