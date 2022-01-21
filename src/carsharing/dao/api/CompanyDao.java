package carsharing.dao.api;

import carsharing.dao.domain.Company;

import java.util.List;

public interface CompanyDao {

    List<Company> getAllCompanies();
    void addCompany(Company c);
    Company getCompanyById(Integer id);

}
