package com.pagewise.model;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private String id;
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private LocalDate publicationDate;
    private boolean isAvailable;
    private int totalCopies;
    private int availableCopies;
    
    public Book(String id, String title, String author, String genre, String isbn, 
                LocalDate publicationDate, int totalCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.isAvailable = totalCopies > 0;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }
    
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { 
        this.availableCopies = availableCopies;
        this.isAvailable = availableCopies > 0;
    }
    
    public void borrowCopy() {
        if (availableCopies > 0) {
            availableCopies--;
            isAvailable = availableCopies > 0;
        }
    }
    
    public void returnCopy() {
        if (availableCopies < totalCopies) {
            availableCopies++;
            isAvailable = true;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Book{id='%s', title='%s', author='%s', genre='%s', available=%d/%d}", 
                           id, title, author, genre, availableCopies, totalCopies);
    }
}
