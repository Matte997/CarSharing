package carsharing.dao.impl;

import carsharing.ConnectionManager;
import carsharing.dao.api.CarDao;
import carsharing.dao.domain.Car;
import carsharing.dao.domain.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {

    Connection connection = null;
    PreparedStatement ptmt = null;
    ResultSet resultSet = null;

    @Override
    public List<Car> getAllCarsFromCompanyId(Integer companyId) {
        List<Car> cars = new ArrayList<>();

        try {
            String queryGetAllCompanies = "SELECT * FROM CAR WHERE COMPANY_ID = " + companyId;

            connection = ConnectionManager.getConnection();
            ptmt = connection.prepareStatement(queryGetAllCompanies);
            resultSet = ptmt.executeQuery();

            if (resultSet == null) return new ArrayList<>();

            while (resultSet.next()) {
                cars.add(new Car(resultSet.getInt("ID"), resultSet.getString("NAME"),
                        resultSet.getInt("COMPANY_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    @Override
    public void addCar(Car c) {
        try {
            String insertCar = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES(" + "'" + c.getName() + "','" + c.getCompanyId() + "')";

            connection = ConnectionManager.getConnection();
            ptmt = connection.prepareStatement(insertCar);
            ptmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Car> getNotRentedCarsFromCompanyId(Integer companyId) {
        List<Car> cars = new ArrayList<>();

        try {
            String queryGetAllNotRentedCars = "SELECT * FROM CAR car LEFT JOIN CUSTOMER cus ON " +
                    "car.ID = cus.RENTED_CAR_ID WHERE cus.RENTED_CAR_ID IS NULL AND COMPANY_ID = " + companyId;

            connection = ConnectionManager.getConnection();
            ptmt = connection.prepareStatement(queryGetAllNotRentedCars);
            resultSet = ptmt.executeQuery();

            if (resultSet == null) return new ArrayList<>();

            while (resultSet.next()) {
                cars.add(new Car(resultSet.getInt("ID"), resultSet.getString("NAME"),
                        resultSet.getInt("COMPANY_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

}