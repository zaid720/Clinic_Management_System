package Models;

import java.time.LocalDateTime;
import personal.Personal;

public class MUser extends Personal {

    private String username, password, role, full_name, phone;

    public MUser(String username, String password, String role, String full_name, String phone) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.full_name = full_name;
        this.phone = phone;
    }

    public MUser(int id, String username, String password, String role, String full_name, String phone, LocalDateTime created_at) {
        super(id, created_at);
        this.username = username;
        this.password = password;
        this.role = role;
        this.full_name = full_name;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public MUser() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getPhone() {
        return phone;
    }

}
