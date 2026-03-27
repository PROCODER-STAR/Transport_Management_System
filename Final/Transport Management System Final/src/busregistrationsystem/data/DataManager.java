package busregistrationsystem.data;

import busregistrationsystem.model.Admin;
import busregistrationsystem.model.BusRoute;
import busregistrationsystem.model.Driver;
import busregistrationsystem.model.Registration;
import busregistrationsystem.model.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {

    private static final String DATA_DIR = "data";
    private static final String USERS_FILE;
    private static final String ADMINS_FILE;
    private static final String DRIVERS_FILE;
    private static final String ROUTES_FILE;
    private static final String REGISTRATIONS_FILE;

    static {
        USERS_FILE = DATA_DIR + File.separator + "users.dat";
        ADMINS_FILE = DATA_DIR + File.separator + "admins.dat";
        DRIVERS_FILE = DATA_DIR + File.separator + "drivers.dat";
        ROUTES_FILE = DATA_DIR + File.separator + "routes.dat";
        REGISTRATIONS_FILE = DATA_DIR + File.separator + "registrations.dat";
    }

    private Map<String, User> users = new HashMap();
    private Map<String, Admin> admins = new HashMap();
    private Map<String, Driver> drivers = new HashMap();
    private Map<Integer, BusRoute> routes = new HashMap();
    private List<Registration> registrations = new ArrayList();
    private static DataManager instance;

    private DataManager() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            if (!dataDir.mkdir()) {
                System.err.println("Error: Failed to create data directory");
            }
        }

        this.loadData();
        if (this.admins.isEmpty()) {
            this.initializeDefaultData();
        }
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }

        return instance;
    }

    private void initializeDefaultData() {
        Admin admin = new Admin("admin", "admin123", "System Administrator");
        this.addAdmin(admin);
        BusRoute route1 = new BusRoute(1, "Main Campus Route", "University Main Gate", "Hostel Area", "07:30 AM", "08:30 AM", 50);
        route1.addStop("Faculty Housing");
        route1.addStop("Library");
        route1.addStop("Science Block");
        BusRoute route2 = new BusRoute(2, "City Route", "University Main Gate", "City Center", "08:00 AM", "09:30 AM", 40);
        route2.addStop("Market Area");
        route2.addStop("Hospital");
        route2.addStop("Shopping Mall");
        BusRoute route3 = new BusRoute(3, "Express Route", "University Main Gate", "Business District", "07:00 AM", "08:15 AM", 30);
        route3.addStop("Technology Park");
        route3.addStop("Research Center");
        this.addRoute(route1);
        this.addRoute(route2);
        this.addRoute(route3);
        Driver driver = new Driver("driver1", "driver123", "John Driver", 1);
        this.addDriver(driver);
        this.saveData();
    }

    private void loadData() {
        try {
            loadUsers();
            loadAdmins();
            loadDrivers();
            loadRoutes();
            loadRegistrations();
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadUsers() throws IOException, ClassNotFoundException {
        File usersFile = new File(USERS_FILE);
        if (usersFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFile))) {
                this.users = (Map<String, User>) ois.readObject();
            }
        }
    }

    private void loadAdmins() throws IOException, ClassNotFoundException {
        File adminsFile = new File(ADMINS_FILE);
        if (adminsFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(adminsFile))) {
                this.admins = (Map<String, Admin>) ois.readObject();
            }
        }
    }

    private void loadDrivers() throws IOException, ClassNotFoundException {
        File driversFile = new File(DRIVERS_FILE);
        if (driversFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(driversFile))) {
                this.drivers = (Map<String, Driver>) ois.readObject();
            }
        }
    }

    private void loadRoutes() throws IOException, ClassNotFoundException {
        File routesFile = new File(ROUTES_FILE);
        if (routesFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(routesFile))) {
                this.routes = (Map<Integer, BusRoute>) ois.readObject();
            }
        }
    }

    private void loadRegistrations() throws IOException, ClassNotFoundException {
        File registrationsFile = new File(REGISTRATIONS_FILE);
        if (registrationsFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(registrationsFile))) {
                this.registrations = (List<Registration>) ois.readObject();
                if (!this.registrations.isEmpty()) {
                    int maxId = this.registrations.stream()
                            .mapToInt(Registration::getRegistrationId)
                            .max()
                            .orElse(0);
                    Registration.updateNextId(maxId);
                }
            }
        }
    }

    public void saveData() {
        try {
            saveUsers();
            saveAdmins();
            saveDrivers();
            saveRoutes();
            saveRegistrations();
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveUsers() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(this.users);
        }
    }

    private void saveAdmins() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ADMINS_FILE))) {
            oos.writeObject(this.admins);
        }
    }

    private void saveDrivers() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DRIVERS_FILE))) {
            oos.writeObject(this.drivers);
        }
    }

    private void saveRoutes() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ROUTES_FILE))) {
            oos.writeObject(this.routes);
        }
    }

    private void saveRegistrations() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REGISTRATIONS_FILE))) {
            oos.writeObject(this.registrations);
        }
    }

    public void addUser(User user) {
        this.users.put(user.getEmail(), user);
        this.saveData();
    }

    public User getUser(String email) {
        return (User) this.users.get(email);
    }

    public boolean authenticateUser(String email, String password) {
        User user = this.users.get(email);
        return user != null && user.verifyPassword(password);
    }

    public Collection<User> getAllUsers() {
        return this.users.values();
    }

    public void addAdmin(Admin admin) {
        this.admins.put(admin.getUsername(), admin);
        this.saveData();
    }

    public Admin getAdmin(String username) {
        return (Admin) this.admins.get(username);
    }

    public boolean authenticateAdmin(String username, String password) {
        Admin admin = this.admins.get(username);
        return admin != null && admin.verifyPassword(password);
    }

    public void addDriver(Driver driver) {
        this.drivers.put(driver.getUsername(), driver);
        this.saveData();
    }

    public Driver getDriver(String username) {
        return (Driver) this.drivers.get(username);
    }

    public boolean authenticateDriver(String username, String password) {
        Driver driver = this.drivers.get(username);
        return driver != null && driver.verifyPassword(password);
    }

    public Collection<Driver> getAllDrivers() {
        return this.drivers.values();
    }

    public void addRoute(BusRoute route) {
        this.routes.put(route.getRouteNumber(), route);
        this.saveData();
    }

    public BusRoute getRoute(int routeNumber) {
        return (BusRoute) this.routes.get(routeNumber);
    }

    public Collection<BusRoute> getAllRoutes() {
        return this.routes.values();
    }

    public void addRegistration(Registration registration) {
        this.registrations.add(registration);
        BusRoute route = (BusRoute) this.routes.get(registration.getRouteNumber());
        if (route != null) {
            route.incrementRegisteredUsers();
        }

        this.saveData();
    }

    public List<Registration> getAllRegistrations() {
        return new ArrayList(this.registrations);
    }

    public List<Registration> getRegistrationsByUser(String email) {
        List<Registration> userRegistrations = new ArrayList();

        for (Registration reg : this.registrations) {
            if (reg.getEmail().equals(email)) {
                userRegistrations.add(reg);
            }
        }

        return userRegistrations;
    }

    public void updateRegistrationStatus(int registrationId, boolean verified) {
        for (Registration reg : this.registrations) {
            if (reg.getRegistrationId() == registrationId) {
                reg.setPaymentVerified(verified);
                User user = (User) this.users.get(reg.getEmail());
                if (user != null) {
                    user.setPaymentVerified(verified);
                }

                this.saveData();
                break;
            }
        }

    }

    public double getTotalRevenue() {
        double total = (double) 0.0F;

        for (Registration reg : this.registrations) {
            if (reg.isPaymentVerified()) {
                total += reg.getPaymentAmount();
            }
        }

        return total;
    }

    public Map<Integer, Integer> getRouteRegistrationCounts() {
        Map<Integer, Integer> counts = new HashMap();

        for (BusRoute route : this.routes.values()) {
            counts.put(route.getRouteNumber(), route.getRegisteredUsers());
        }

        return counts;
    }

    public int getMostPopularRoute() {
        int maxCount = -1;
        int popularRoute = -1;

        for (Map.Entry<Integer, BusRoute> entry : this.routes.entrySet()) {
            if (((BusRoute) entry.getValue()).getRegisteredUsers() > maxCount) {
                maxCount = ((BusRoute) entry.getValue()).getRegisteredUsers();
                popularRoute = (Integer) entry.getKey();
            }
        }

        return popularRoute;
    }

    public void addDriverUpdate(String username, String update) {
        Driver driver = (Driver) this.drivers.get(username);
        if (driver != null) {
            driver.addUpdate(update);
            this.saveData();
        }

    }

    public List<String> getUpdatesForRoute(int routeNumber) {
        List<String> updates = new ArrayList();

        for (Driver driver : this.drivers.values()) {
            if (driver.getRouteNumber() == routeNumber) {
                updates.addAll(driver.getUpdates());
            }
        }

        return updates;
    }
}
