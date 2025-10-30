package com.pagewise.factory;

import com.pagewise.model.User;

import java.util.UUID;

public class UserFactory implements EntityFactory<User> {
    
    @Override
    public User create(String... params) {
        if (params.length < 3) {
            throw new IllegalArgumentException("User creation requires: name, email, type, [additional params]");
        }
        
        String name = params[0];
        String email = params[1];
        User.UserType type = User.UserType.valueOf(params[2].toUpperCase());
        
        String id = "USER_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
        
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        
        return new User(id, name.trim(), email.toLowerCase(), type);
    }
    
    public User createReader(String name, String email) {
        return create(name, email, "READER");
    }
    
    public User createLibrarian(String name, String email) {
        return create(name, email, "LIBRARIAN");
    }
    
    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }
}
