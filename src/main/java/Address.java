public class Address {
    private String street;
    private Integer home;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHome() {
        return home;
    }

    public void setHome(Integer home) {
        this.home = home;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", home=" + home +
                '}';
    }
    public Address(){}
    public Address(String street, Integer home) {
        this.street = street;
        this.home = home;
    }
}
