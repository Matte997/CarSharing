package carsharing.dao.domain;

public class Company {

    private Integer id;
    private String name;

    public Company(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Company(String name) {
        this(null, name);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getId() + ". " + this.getName();
    }
}
