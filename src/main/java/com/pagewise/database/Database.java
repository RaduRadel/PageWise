package com.pagewise.database;

import com.pagewise.model.Book;
import com.pagewise.model.BorrowingRecord;
import com.pagewise.model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Database {
    private static volatile Database instance;
    private static final Object lock = new Object();
    
    private final Map<String, Book> books;
    private final Map<String, User> users;
    private final Map<String, BorrowingRecord> borrowingRecords;
    private final Map<String, Set<String>> userBorrowedBooks;
    private final Map<String, Set<String>> bookBorrowers;
    
    private Database() {
        this.books = new ConcurrentHashMap<>();
        this.users = new ConcurrentHashMap<>();
        this.borrowingRecords = new ConcurrentHashMap<>();
        this.userBorrowedBooks = new ConcurrentHashMap<>();
        this.bookBorrowers = new ConcurrentHashMap<>();
    }
    
    public static Database getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }
    
    public void addBook(Book book) {
        books.put(book.getId(), book);
    }
    
    public Book getBook(String id) {
        return books.get(id);
    }
    
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }
    
    public void updateBook(Book book) {
        books.put(book.getId(), book);
    }
    
    public void removeBook(String id) {
        books.remove(id);
        bookBorrowers.remove(id);
    }
    
    public void addUser(User user) {
        users.put(user.getId(), user);
    }
    
    public User getUser(String id) {
        return users.get(id);
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    
    public void updateUser(User user) {
        users.put(user.getId(), user);
    }
    
    public void removeUser(String id) {
        users.remove(id);
        userBorrowedBooks.remove(id);
    }
    
    public void addBorrowingRecord(BorrowingRecord record) {
        borrowingRecords.put(record.getId(), record);
        
        userBorrowedBooks.computeIfAbsent(record.getUserId(), k -> new HashSet<>())
                        .add(record.getBookId());
        
        bookBorrowers.computeIfAbsent(record.getBookId(), k -> new HashSet<>())
                    .add(record.getUserId());
    }
    
    public BorrowingRecord getBorrowingRecord(String id) {
        return borrowingRecords.get(id);
    }
    
    public List<BorrowingRecord> getAllBorrowingRecords() {
        return new ArrayList<>(borrowingRecords.values());
    }
    
    public void updateBorrowingRecord(BorrowingRecord record) {
        borrowingRecords.put(record.getId(), record);
    }
    
    public void removeBorrowingRecord(String id) {
        BorrowingRecord record = borrowingRecords.remove(id);
        if (record != null) {
            Set<String> userBooks = userBorrowedBooks.get(record.getUserId());
            if (userBooks != null) {
                userBooks.remove(record.getBookId());
            }
            
            Set<String> bookUsers = bookBorrowers.get(record.getBookId());
            if (bookUsers != null) {
                bookUsers.remove(record.getUserId());
            }
        }
    }
    
    public List<Book> getBooksByUser(String userId) {
        Set<String> bookIds = userBorrowedBooks.get(userId);
        if (bookIds == null) return new ArrayList<>();
        
        return bookIds.stream()
                .map(books::get)
                .filter(Objects::nonNull)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public List<User> getUsersByBook(String bookId) {
        Set<String> userIds = bookBorrowers.get(bookId);
        if (userIds == null) return new ArrayList<>();
        
        return userIds.stream()
                .map(users::get)
                .filter(Objects::nonNull)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public List<BorrowingRecord> getBorrowingRecordsByUser(String userId) {
        return borrowingRecords.values().stream()
                .filter(record -> record.getUserId().equals(userId))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public List<BorrowingRecord> getBorrowingRecordsByBook(String bookId) {
        return borrowingRecords.values().stream()
                .filter(record -> record.getBookId().equals(bookId))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public List<BorrowingRecord> getOverdueRecords() {
        return borrowingRecords.values().stream()
                .filter(BorrowingRecord::isOverdue)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public int getTotalBooks() {
        return books.size();
    }
    
    public int getTotalUsers() {
        return users.size();
    }
    
    public int getTotalBorrowingRecords() {
        return borrowingRecords.size();
    }
    
    public int getActiveBorrowings() {
        return (int) borrowingRecords.values().stream()
                .filter(record -> record.getStatus() == BorrowingRecord.BorrowingStatus.BORROWED)
                .count();
    }
    
    public void clearAll() {
        books.clear();
        users.clear();
        borrowingRecords.clear();
        userBorrowedBooks.clear();
        bookBorrowers.clear();
    }
}
