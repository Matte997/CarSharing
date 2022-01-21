package carsharing.dao.domain;

public class Car {

    private Integer id;
    private String name;
    private Integer companyId;

    public Car(Integer id, String name, Integer companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    public Car(String name, Integer companyId) {
        this(null, name, companyId);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    @Override
    public String toString() {
        return this.getId() + ". " + this.getName();
    }
}