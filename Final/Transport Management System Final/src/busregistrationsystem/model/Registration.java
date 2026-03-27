package busregistrationsystem.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class Registration implements Serializable {

    private static long serialVersionUID = 4L;

    private static AtomicInteger nextId = new AtomicInteger(1);
    private int registrationId;
    private LocalDateTime registrationDate;
    private double paymentAmount;

    // Client data
    private String email;
    private String universityId;
    private String designation;
    private int routeNumber;
    private boolean paymentVerified;
    private String name;

    // Constructor
    public Registration() {
        this.registrationId = nextId.getAndIncrement();
        this.registrationDate = LocalDateTime.now();
        this.paymentAmount = 5000.0; // Default amount in PKR
    }

    // Constructor with specific user data
    public Registration(String email, String universityId, String designation, int routeNumber, String name) {
        this.email = email;
        this.universityId = universityId;
        this.designation = designation;
        this.routeNumber = routeNumber;
        this.paymentVerified = false;
        this.name = name;
        this.registrationId = nextId.getAndIncrement();
        this.registrationDate = LocalDateTime.now();
        this.paymentAmount = 5000.0; // Default amount in PKR
    }

    // Getter and setter methods
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter for registrationId
    public int getRegistrationId() {
        return registrationId;
    }

    // Method to update the nextId value based on existing registrations
    public static synchronized void updateNextId(int maxId) {
        nextId.set(Math.max(nextId.get(), maxId + 1));
    }

    // Getter for registrationDate formatted as a string
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return registrationDate.format(formatter);
    }

    // Getter and Setter for paymentAmount
    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    // Get the payment status
    public String getStatus() {
        return paymentVerified ? "Approved" : "Pending";
    }

    @Override
    public String toString() {
        return "Registration{"
                + "registrationId=" + registrationId
                + ", email='" + email + '\''
                + ", name='" + name + '\''
                + ", universityId='" + universityId + '\''
                + ", designation='" + designation + '\''
                + ", routeNumber=" + routeNumber
                + ", registrationDate=" + getFormattedDate()
                + ", paymentStatus=" + getStatus()
                + '}';
    }
}