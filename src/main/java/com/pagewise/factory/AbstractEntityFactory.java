package com.pagewise.factory;

import com.pagewise.model.Book;
import com.pagewise.model.User;

import java.time.LocalDate;

public abstract class AbstractEntityFactory {
    
    public static BookFactory getBookFactory() {
        return new BookFactory();
    }
    
    public static UserFactory getUserFactory() {
        return new UserFactory();
    }
    
    public static Book createBook(String title, String author, String genre, String isbn, LocalDate publicationDate, int copies) {
        return getBookFactory().create(title, author, genre, isbn, publicationDate.toString(), String.valueOf(copies));
    }

    public static User createUser(String name, String email, User.UserType type) {
        return getUserFactory().create(name, email, type.toString());
    }
}
