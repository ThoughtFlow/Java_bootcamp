package module07_io;

import java.io.Serializable;

class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	static int baseVacationDays = 10;
    
	private String name;
    private String ssn;
    private String emailAddress;
    private int yearOfBirth;
    private int extraVacationDays = 0;
    
    public Employee(String name, String ssn, String emailAddress) {
        this.name = name;
        this.ssn = ssn;
        this.emailAddress = emailAddress;
    }

    public static void setBaseVacationDays(int days) {
        baseVacationDays = days < 10? 10 : days;
    }

    public static int getBaseVacationDays() {
        return baseVacationDays;
    }

    public void setExtraVacationDays(int days) {
        extraVacationDays = days < 0? 0 : days;
    }

    public int getExtraVacationDays() {
        return extraVacationDays;
    }

    public void setYearOfBirth(int year) {
        yearOfBirth = year;
    }

    public int getVacationDays() {
        return baseVacationDays + extraVacationDays;
    }

    public void print() {
        System.out.println("Name: " + name);
        System.out.println("SSN: " + ssn);
        System.out.println("Email Address: " + emailAddress);
        System.out.println("Year Of Birth: " + yearOfBirth);
        System.out.println("Vacation Days: " + getVacationDays());
    }
}