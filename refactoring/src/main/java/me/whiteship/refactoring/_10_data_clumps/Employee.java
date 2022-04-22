package me.whiteship.refactoring._10_data_clumps;


public class Employee {

    private String name;

    private TelephoneNumber personalTelephoneNumber;
    
    public Employee(String name, TelephoneNumber personalTelephoneNumber) {
        this.name = name;
        this.personalTelephoneNumber = personalTelephoneNumber;
    }

    public String personalPhoneNumber() {
        return this.personalTelephoneNumber.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TelephoneNumber getPersonalTelephoneNumber() {
        return personalTelephoneNumber;
    }
}
