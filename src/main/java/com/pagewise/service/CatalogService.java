package com.pagewise.service;

import com.pagewise.database.Database;
import com.pagewise.model.Book;
import com.pagewise.strategy.SearchStrategy;
import com.pagewise.strategy.SortStrategy;

import java.util.List;

public class CatalogService {
    private final Database database;
    private SearchStrategy searchStrategy;
    private SortStrategy sortStrategy;
    
    public CatalogService() {
        this.database = Database.getInstance();
        this.searchStrategy = new com.pagewise.strategy.PartialSearchStrategy();
        this.sortStrategy = new com.pagewise.strategy.TitleSortStrategy();
    }
    
    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }
    
    public void setSortStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }
    
    public List<Book> searchBooks(String query) {
        List<Book> allBooks = database.getAllBooks();
        return searchStrategy.search(allBooks, query);
    }
    
    public List<Book> sortBooks() {
        List<Book> allBooks = database.getAllBooks();
        return sortStrategy.sort(allBooks);
    }

    public List<Book> searchAndSortBooks(String query) {
        List<Book> searchResults = searchBooks(query);
        return sortStrategy.sort(searchResults);
    }

    public List<Book> getAllBooks() {
        return sortBooks();
    }
    
    public List<Book> getAvailableBooks() {
        return database.getAllBooks().stream()
                .filter(Book::isAvailable)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<Book> getBooksByGenre(String genre) {
        return database.getAllBooks().stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .collect(java.util.stream.Collectors.toList());
    }
    
    public List<Book> getBooksByAuthor(String author) {
        return database.getAllBooks().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(java.util.stream.Collectors.toList());
    }
    
    public Book getBookById(String id) {
        return database.getBook(id);
    }
    
    public void addBook(Book book) {
        database.addBook(book);
    }

    public void updateBook(Book book) {
        database.updateBook(book);
    }
    
    public void removeBook(String id) {
        database.removeBook(id);
    }
}
