package busregistrationsystem.model;

import java.io.Serializable;

public class User extends Client implements Serializable {

    private static final long serialVersionUID = 3L;

    // Default constructor
    public User() {
        super();
    }

    // Constructor with basic fields
    public User(String name, String email, String password) {
        super(email, password, name, email, "", "", 0, false);
    }

    // Full constructor with all attributes
    public User(String name, String email, String password, String universityId, String designation, int routeNumber) {
        super(email, password, name, email, universityId, designation, routeNumber, false);
    }

    // ======== Getter and Setter for `name` ========
    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public String getPassword() {
        return super.getPassword();
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }

    // ======== Overridden Methods from `Client` ========
    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getUniversityId() {
        return super.getUniversityId();
    }

    @Override
    public void setUniversityId(String universityId) {
        super.setUniversityId(universityId);
    }

    @Override
    public String getDesignation() {
        return super.getDesignation();
    }

    @Override
    public void setDesignation(String designation) {
        super.setDesignation(designation);
    }

    @Override
    public int getRouteNumber() {
        return super.getRouteNumber();
    }

    @Override
    public void setRouteNumber(int routeNumber) {
        super.setRouteNumber(routeNumber);
    }

    @Override
    public boolean isPaymentVerified() {
        return super.isPaymentVerified();
    }

    @Override
    public void setPaymentVerified(boolean paymentVerified) {
        super.setPaymentVerified(paymentVerified);
    }

    // ======== `toString` Method ========
    @Override
    public String toString() {
        return "User{"
                + "name='" + getName() + '\''
                + ", email='" + getEmail() + '\''
                + ", universityId='" + getUniversityId() + '\''
                + ", designation='" + getDesignation() + '\''
                + ", routeNumber=" + getRouteNumber()
                + ", paymentVerified=" + isPaymentVerified()
                + '}';
    }
}