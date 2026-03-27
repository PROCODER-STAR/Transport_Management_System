package busregistrationsystem.model;

import java.io.Serializable;

public class Admin extends Person implements Serializable {

    private static final long serialVersionUID = 6L;

    public Admin() {
        super();
    }

    public Admin(String username, String password, String name) {
        super(username, password, name);
    }

    @Override
    public String toString() {
        return "Admin{"
                + "username='" + getUsername() + '\''
                + ", name='" + getName() + '\''
                + '}';
    }
}