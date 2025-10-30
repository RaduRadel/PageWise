package com.pagewise.model;

import java.time.LocalDate;
import java.util.Objects;

public class User {
    private String id;
    private String name;
    private String email;
    private UserType type;
    private LocalDate registrationDate;
    private boolean isActive;
    
    public enum UserType {
        READER, LIBRARIAN
    }
    
    public User(String id, String name, String email, UserType type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
        this.registrationDate = LocalDate.now();
        this.isActive = true;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public UserType getType() { return type; }
    public void setType(UserType type) { this.type = type; }
    
    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
    
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    
    public boolean isLibrarian() {
        return type == UserType.LIBRARIAN;
    }
    
    public boolean isReader() {
        return type == UserType.READER;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("User{id='%s', name='%s', email='%s', type=%s}", 
                           id, name, email, type);
    }
}
