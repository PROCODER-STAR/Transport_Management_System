package busregistrationsystem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Driver extends Person implements Serializable {

    private static final long serialVersionUID = 7L;

    private int routeNumber;
    private List<String> updates;

    public Driver() {
        super();
        this.updates = new ArrayList<>();
    }

    public Driver(String username, String password, String name, int routeNumber) {
        super(username, password, name);
        this.routeNumber = routeNumber;
        this.updates = new ArrayList<>();
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

    public List<String> getUpdates() {
        return updates;
    }

    public void addUpdate(String update) {
        this.updates.add(update);
    }

    @Override
    public String toString() {
        return "Driver{"
                + "username='" + getUsername() + '\''
                + ", name='" + getName() + '\''
                + ", routeNumber=" + routeNumber
                + '}';
    }
}