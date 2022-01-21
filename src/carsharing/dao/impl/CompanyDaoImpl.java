package carsharing.dao.impl;

import carsharing.ConnectionManager;
import carsharing.dao.api.CompanyDao;
import carsharing.dao.domain.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao {

    Connection connection = null;
    PreparedStatement ptmt = null;
    ResultSet resultSet = null;

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();

        try {
            String queryGetAllCompanies = "SELECT * FROM COMPANY";

            connection = ConnectionManager.getConnection();
            ptmt = connection.prepareStatement(queryGetAllCompanies);
            resultSet = ptmt.executeQuery();

            if (resultSet == null) return new ArrayList<>();

            while (resultSet.next()) {
                companies.add(new Company(resultSet.getInt("ID"), resultSet.getString("NAME")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companies;
    }

    @Override
    public void addCompany(Company c) {
        try {
            String insertCompany = "INSERT INTO COMPANY (NAME) VALUES(" + "'" + c.getName() + "')";

            connection = ConnectionManager.getConnection();
            ptmt = connection.prepareStatement(insertCompany);
            ptmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Company getCompanyById(Integer id) {
        return getAllCompanies().get(id - 1);
    }
}
