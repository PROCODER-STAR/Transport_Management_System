package busregistrationsystem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BusRoute implements Serializable {

    private static final long serialVersionUID = 5L;

    private int routeNumber;
    private String routeName;
    private String startPoint;
    private String endPoint;
    private List<String> stops;
    private String departureTime;
    private String arrivalTime;
    private int capacity;
    private int registeredUsers;

    public BusRoute() {
        this.stops = new ArrayList<>();
        this.registeredUsers = 0;
    }

    public BusRoute(int routeNumber, String routeName, String startPoint, String endPoint,
                    String departureTime, String arrivalTime, int capacity) {
        this.routeNumber = routeNumber;
        this.routeName = routeName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.stops = new ArrayList<>();
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.capacity = capacity;
        this.registeredUsers = 0;
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public List<String> getStops() {
        return stops;
    }

    public void addStop(String stop) {
        this.stops.add(stop);
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(int registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public void incrementRegisteredUsers() {
        this.registeredUsers++;
    }

    @Override
    public String toString() {
        return "Route " + routeNumber + ": " + routeName
                + " (" + startPoint + " → " + endPoint + "), "
                + "Departure: " + departureTime + ", Arrival: " + arrivalTime;
    }
}