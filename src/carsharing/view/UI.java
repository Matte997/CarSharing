package carsharing.view;

import carsharing.ConnectionManager;
import carsharing.dao.domain.Car;
import carsharing.dao.domain.Company;
import carsharing.dao.domain.Customer;
import carsharing.dao.impl.CarDaoImpl;
import carsharing.dao.impl.CompanyDaoImpl;
import carsharing.dao.impl.CustomerDaoImpl;

import java.util.Scanner;

public class UI {

    private static final Scanner scan = new Scanner(System.in);
    private static final CompanyDaoImpl COMPANY_DAO = new CompanyDaoImpl();
    private static final CarDaoImpl CAR_DAO = new CarDaoImpl();
    private static final CustomerDaoImpl CUSTOMER_DAO = new CustomerDaoImpl();
    private String carRentedByCustomer;
    private String companyFromRentedCar;
    private Boolean isManager = false;
    private Boolean isCustomerBackToStartMenu = false;
    private Integer customerIdToUpdate = -1;

    public void databaseSetup() {
        ConnectionManager.createTableCompanyAndTableCar();
    }

    public void start() {
        databaseSetup();
        ui();
    }

    public void ui() {
        while (true) {
            isCustomerBackToStartMenu = false;
            System.out.printf("%s%n%s%n%s%n%s%n", "1. Log in as a manager",
                    "2. Log in as a customer", "3. Create a customer", "0. Exit");

            int menuChoice = scan.nextInt();
            scan.nextLine();

            if (menuChoice == 0) {
                break;
            }

            if (menuChoice == 1) {
                isManager = true;
                logAsManagerMenu();
            }

            if (menuChoice == 2) {
                isManager = false;
                logAsCustomerMenu();
            }

            if (menuChoice == 3) {
                isManager = false;
                createCustomerMenu();
            }
        }
    }

    public void logAsManagerMenu() {
        int managerMenuChoice = -1;

        while (managerMenuChoice != 0) {
            System.out.printf("%s%n%s%n%s%n", "1. Company list", "2. Create a company", "0. Back");

            managerMenuChoice = scan.nextInt();
            scan.nextLine();

            if (managerMenuChoice == 1) {
                chooseCompany();
            }

            if (managerMenuChoice == 2) {
                menuCreateCompany();
            }
        }
    }

    public void logAsCustomerMenu() {
        while (!isCustomerBackToStartMenu) {
            if (CUSTOMER_DAO.getAllCustomers().isEmpty()) {
                System.out.println("The customer list is empty!");
                break;
            } else {
                System.out.println("Customer list:");

                printAllCustomers();
                System.out.println("0. Back");

                int customerAccount = scan.nextInt();
                scan.nextLine();

                if (customerAccount == 0) break;

                customerIdToUpdate = customerAccount;

                carMenuFromCustomer(customerAccount);
            }
        }
    }

    public void createCustomerMenu() {
        System.out.println("Enter the customer name:");

        String customerName = scan.nextLine();
        createCustomer(customerName);

        System.out.println("The customer was added!");
    }

    public void chooseCompany() {
        if (COMPANY_DAO.getAllCompanies().isEmpty()) {
            System.out.println("The company list is empty!");
        } else {

            if (isManager) {
                System.out.println("Choose the company:");

                printAllCompanies();
                System.out.println("0. Back");

                int managerCompanyChoice = scan.nextInt();
                scan.nextLine();

                if (managerCompanyChoice == 0) {
                    return;
                }

                carMenuFromCompany(managerCompanyChoice);
            } else {
                System.out.println("Choose a company:");

                printAllCompanies();
                System.out.println("0. Back");

                int customerCompanyChoice = scan.nextInt();
                scan.nextLine();

                if (customerCompanyChoice == 0) return;

                carListFromCustomer(customerCompanyChoice);
            }
        }
    }

    public void carMenuFromCompany(Integer companyId) {
        System.out.println("'" + COMPANY_DAO.getCompanyById(companyId).getName() +"' " + "company");

        while(true) {
            System.out.printf("%s%n%s%n%s%n", "1. Car list", "2. Create a car", "0. Back");

            int carChoice = scan.nextInt();
            scan.nextLine();

            if (carChoice == 0) {
                break;
            }

            if (carChoice == 1) {
                if (CAR_DAO.getAllCarsFromCompanyId(companyId).size() == 0) {
                    System.out.println("The car list is empty!");
                } else {
                    System.out.println("Car list:");
                    printAllCarsFromCompanyId(companyId);
                }
            }

            if (carChoice == 2) {
                System.out.println("Enter the car name:");

                String carName = scan.nextLine();
                createCar(carName, companyId);

                System.out.println("The car was added!");
            }
        }
    }

    public void menuCreateCompany() {
        System.out.println("Enter the company name:");

        String companyName = scan.nextLine();
        createCompany(companyName);

        System.out.println("The company was created!");
    }
    
    public void carMenuFromCustomer(Integer customerId) {
        while (true) {
            System.out.printf("%s%n%s%n%s%n%s%n", "1. Rent a car", "2. Return a rented car", "3. My rented car", "0. Back");

            int customerChoice = scan.nextInt();

            if (customerChoice == 0) {
                isCustomerBackToStartMenu = true;
                break;
            }

            if (customerChoice == 1) {
                if (CUSTOMER_DAO.getAllCustomers().get(customerId - 1).getRentedCarId() != 0) {
                    System.out.println("You've already rented a car!");
                } else {
                    chooseCompany();
                }
            }

            if (customerChoice == 2) {
                if (CUSTOMER_DAO.getAllCustomers().get(customerId - 1).getRentedCarId() == 0) {
                    System.out.println("You didn't rent a car!");
                } else {
                    System.out.println("You've returned a rented car!");
                    updateCustomerRentedCarId(customerId, null);
                }
            }

            if (customerChoice == 3) {
                if (CUSTOMER_DAO.getAllCustomers().get(customerId - 1).getRentedCarId() == 0) {
                    System.out.println("You didn't rent a car!");
                } else {
                    System.out.printf("%s%n%s%n%s%n%s%n", "Your rented car",
                            carRentedByCustomer, "Company:",
                            companyFromRentedCar);
                }
            }
        }
    }

    public void carListFromCustomer(Integer companyId) {
        if (CAR_DAO.getNotRentedCarsFromCompanyId(companyId).isEmpty()) {
            System.out.println("There are no cars available for this company");
            return;
        }

        System.out.println("Choose a car:");

        printNotRentedCarsFromCompanyId(companyId);

        System.out.println("0. Back");

        int carChoose = scan.nextInt();
        scan.nextLine();

        if (carChoose == 0) return;

        System.out.println("You rented " + "'" + CAR_DAO.getAllCarsFromCompanyId(companyId).get(carChoose - 1).getName() + "'");
        carRentedByCustomer = CAR_DAO.getAllCarsFromCompanyId(companyId).get(carChoose - 1).getName();
        companyFromRentedCar = COMPANY_DAO.getCompanyById(companyId).getName();

        updateCustomerRentedCarId(customerIdToUpdate, carChoose);
    }

    public void createCar(String carName, Integer companyId) {
        CAR_DAO.addCar(new Car(carName, companyId));
    }

    public void createCompany(String companyName) {
        COMPANY_DAO.addCompany(new Company(companyName));
    }

    public void createCustomer(String customerName) {
        CUSTOMER_DAO.addCustomer(new Customer(customerName));
    }

    public void updateCustomerRentedCarId(Integer customerId, Integer carId) {
        CUSTOMER_DAO.updateRentedCarId(customerId, carId);
    }

    public void printAllCompanies() {
        COMPANY_DAO.getAllCompanies().forEach(
                company -> {
                    System.out.println(company.getId() + ". " + company.getName());
                }
        );
    }

    public void printAllCarsFromCompanyId(Integer companyId) {
        int[] carId = {0};

        CAR_DAO.getAllCarsFromCompanyId(companyId).forEach(car -> System.out.println(++carId[0] + ". " + car.getName())

        );
    }

    public void printNotRentedCarsFromCompanyId(Integer companyId) {
        int[] carId = {0};

        CAR_DAO.getNotRentedCarsFromCompanyId(companyId).forEach(car -> System.out.println(++carId[0] + ". " + car.getName()));
    }

    public void printAllCustomers() {
        CUSTOMER_DAO.getAllCustomers().forEach(customer -> System.out.println(customer.getId() + ". " + customer.getName()));
    }
}