package entity;

import java.util.UUID;

public class UserDetails {


    private UUID userId;
    private String username;
    private String email;
    private String phone;
    private String isActive;
    private String password;
    private String Address;

    public UserDetails(UUID userId, String username, String email, String phone, String isActive, String password, String address) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.isActive = isActive;
        this.password = password;
        Address = address;
    }

    public UserDetails() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", isActive='" + isActive + '\'' +
                ", password='" + password + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }
}
