package carsharing.dao.api;

import carsharing.dao.domain.Car;
import carsharing.dao.domain.Company;

import java.util.List;

public interface CarDao {

    List<Car> getAllCarsFromCompanyId(Integer companyId);
    void addCar(Car c);
    List<Car> getNotRentedCarsFromCompanyId(Integer companyId);


}