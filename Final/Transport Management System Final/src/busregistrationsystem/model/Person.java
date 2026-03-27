package busregistrationsystem.model;

import busregistrationsystem.util.PasswordUtil;
import java.io.Serializable;

public abstract class Person implements Serializable {

    private static long serialVersionUID = 1L;

    private String username;
    private String passwordHash;
    private String salt;
    private String name;

    public Person() {
        this.salt = PasswordUtil.generateSalt();
    }

    public Person(String username, String password, String name) {
        this.username = username;
        this.salt = PasswordUtil.generateSalt();
        this.passwordHash = PasswordUtil.hashPassword(password, salt);
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    // Instead of returning the actual password hash, we return null for security
    public String getPassword() {
        return null;
    }

    public void setPassword(String password) {
        if (salt == null) {
            this.salt = PasswordUtil.generateSalt();
        }
        this.passwordHash = PasswordUtil.hashPassword(password, salt);
    }

    public boolean verifyPassword(String password) {
        return PasswordUtil.verifyPassword(password, passwordHash, salt);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{"
                + "username='" + username + '\''
                + ", name='" + name + '\''
                + '}';
    }
}