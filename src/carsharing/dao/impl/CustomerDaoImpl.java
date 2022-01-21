package carsharing.dao.impl;

import carsharing.ConnectionManager;
import carsharing.dao.api.CustomerDao;
import carsharing.dao.domain.Company;
import carsharing.dao.domain.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    Connection connection = null;
    PreparedStatement ptmt = null;
    ResultSet resultSet = null;

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try {
            String queryGetAllCompanies = "SELECT * FROM CUSTOMER";

            connection = ConnectionManager.getConnection();
            ptmt = connection.prepareStatement(queryGetAllCompanies);
            resultSet = ptmt.executeQuery();

            if (resultSet == null) return new ArrayList<>();

            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getInt("ID"), resultSet.getString("NAME"),
                        resultSet.getInt("RENTED_CAR_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public void addCustomer(Customer c) {
        try {
            String insertCompany = "INSERT INTO CUSTOMER (NAME) VALUES(" + "'" + c.getName() + "')";

            connection = ConnectionManager.getConnection();
            ptmt = connection.prepareStatement(insertCompany);
            ptmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRentedCarId(Integer customerId, Integer rentedCarId) {
        try {
            String insertCompany = "UPDATE CUSTOMER SET RENTED_CAR_ID = " + rentedCarId + " WHERE " + "ID = " + customerId;

            connection = ConnectionManager.getConnection();
            ptmt = connection.prepareStatement(insertCompany);
            ptmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
