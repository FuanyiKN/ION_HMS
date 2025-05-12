package cosc214.ion_hms.models;

import org.mindrot.jbcrypt.BCrypt;
import java.util.regex.Pattern;

public class User {
    private String id;
    private String name; // Added name field
    private String hashedPassword;
    private Role role;
    private String email;
    private String contactNumber;

    public enum Role {
        ADMIN, DOCTOR, PATIENT
    }

    // Constructor for registration (hashes password)
    public User(String id, String name, String rawPassword, Role role, String email, String contactNumber) {
        this.id = id;
        this.name = name; // Set name
        this.hashedPassword = hashPassword(rawPassword);
        this.role = role;
        setEmail(email);
        setContactNumber(contactNumber);
    }

    // Constructor for loading from DB (password already hashed)
    public User(String id, String name, String hashedPassword, Role role, String email, String contactNumber, boolean alreadyHashed) {
        this.id = id;
        this.name = name; // Set name
        this.hashedPassword = hashedPassword;
        this.role = role;
        setEmail(email);
        setContactNumber(contactNumber);
    }

    // Password hashing logic
    private String hashPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    // Password verification
    public boolean verifyPassword(String inputPassword) {
        return BCrypt.checkpw(inputPassword, this.hashedPassword);
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    // Removed setter for id to make it immutable

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && !Pattern.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        if (contactNumber != null && !Pattern.matches("^\\+?[0-9]{7,20}$", contactNumber)) {
            throw new IllegalArgumentException("Invalid contact number format");
        }
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' + // Include name in toString
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
}
