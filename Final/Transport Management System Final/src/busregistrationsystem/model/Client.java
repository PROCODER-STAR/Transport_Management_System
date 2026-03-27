package busregistrationsystem.model;

import java.io.Serializable;

public abstract class Client extends Person implements Serializable {

    private static final long serialVersionUID = 2L;

    private String email;
    private String universityId;
    private String designation;
    private int routeNumber;
    private boolean paymentVerified;

    public Client() {
        super();
    }

    public Client(String username, String password, String name,
                  String email, String universityId, String designation,
                  int routeNumber, boolean paymentVerified) {
        super(username, password, name);
        this.email = email;
        this.universityId = universityId;
        this.designation = designation;
        this.routeNumber = routeNumber;
        this.paymentVerified = paymentVerified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

    public boolean isPaymentVerified() {
        return paymentVerified;
    }

    public void setPaymentVerified(boolean paymentVerified) {
        this.paymentVerified = paymentVerified;
    }

    @Override
    public String toString() {
        return "Client{"
                + "name='" + getName() + '\''
                + ", email='" + email + '\''
                + ", universityId='" + universityId + '\''
                + ", designation='" + designation + '\''
                + ", routeNumber=" + routeNumber
                + ", paymentVerified=" + paymentVerified
                + '}';
    }
}