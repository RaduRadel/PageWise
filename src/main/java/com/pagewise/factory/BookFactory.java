package com.pagewise.factory;

import com.pagewise.model.Book;

import java.time.LocalDate;
import java.util.UUID;

public class BookFactory implements EntityFactory<Book> {
    private static final int DEFAULT_COPIES = 1;
    
    @Override
    public Book create(String... params) {
        if (params.length < 5) {
            throw new IllegalArgumentException("Book creation requires: title, author, genre, isbn, publicationDate");
        }
        
        String title = params[0];
        String author = params[1];
        String genre = params[2];
        String isbn = params[3];
        LocalDate publicationDate = LocalDate.parse(params[4]);
        int copies = params.length > 5 ? Integer.parseInt(params[5]) : DEFAULT_COPIES;
        
        String id = "BOOK_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        if (!isValidISBN(isbn)) {
            throw new IllegalArgumentException("Invalid ISBN format: " + isbn);
        }
        
        if (publicationDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Publication date cannot be in the future");
        }
        
        if (copies <= 0) {
            throw new IllegalArgumentException("Number of copies must be positive");
        }
        
        return new Book(id, title, author, genre, isbn, publicationDate, copies);
    }

    public Book createWithDefaults(String title, String author, String genre, String isbn) {
        return create(title, author, genre, isbn, LocalDate.now().toString(), String.valueOf(DEFAULT_COPIES));
    }

    public Book createWithDate(String title, String author, String genre, String isbn, LocalDate publicationDate) {
        return create(title, author, genre, isbn, publicationDate.toString(), String.valueOf(DEFAULT_COPIES));
    }
    
    private boolean isValidISBN(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }
        String cleanISBN = isbn.replaceAll("[^0-9X]", "");
        return cleanISBN.length() == 10 || cleanISBN.length() == 13;
    }
}
