package com.pagewise.observer;

import com.pagewise.model.Book;
import com.pagewise.model.BorrowingRecord;
import com.pagewise.model.User;
import java.time.LocalDateTime;

public class LibraryEvent {
    public enum EventType {
        BOOK_BORROWED,
        BOOK_RETURNED,
        BOOK_ADDED,
        BOOK_UPDATED,
        BOOK_REMOVED,
        DUE_DATE_APPROACHING,
        BOOK_OVERDUE,
        USER_REGISTERED,
        USER_UPDATED
    }
    
    private final EventType type;
    private final String message;
    private final LocalDateTime timestamp;
    private final Book book;
    private final User user;
    private final BorrowingRecord borrowingRecord;
    private final Object additionalData;
    
    public LibraryEvent(EventType type, String message, Book book, User user, BorrowingRecord borrowingRecord, Object additionalData) {
        this.type = type;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.book = book;
        this.user = user;
        this.borrowingRecord = borrowingRecord;
        this.additionalData = additionalData;
    }
    
    public static LibraryEvent bookBorrowed(Book book, User user, BorrowingRecord record) {
        return new LibraryEvent(
            EventType.BOOK_BORROWED,
            String.format("Book '%s' borrowed by %s", book.getTitle(), user.getName()),
            book, user, record, null
        );
    }
    
    public static LibraryEvent bookReturned(Book book, User user, BorrowingRecord record) {
        return new LibraryEvent(
            EventType.BOOK_RETURNED,
            String.format("Book '%s' returned by %s", book.getTitle(), user.getName()),
            book, user, record, null
        );
    }
    
    public static LibraryEvent bookAdded(Book book, User librarian) {
        return new LibraryEvent(
            EventType.BOOK_ADDED,
            String.format("New book '%s' added to catalog by %s", book.getTitle(), librarian.getName()),
            book, librarian, null, null
        );
    }
    
    public static LibraryEvent dueDateApproaching(BorrowingRecord record) {
        return new LibraryEvent(
            EventType.DUE_DATE_APPROACHING,
            String.format("Book '%s' is due soon (Due: %s)", 
                         record.getBookId(), record.getDueDate()),
            null, null, record, null
        );
    }
    
    public static LibraryEvent bookOverdue(BorrowingRecord record) {
        return new LibraryEvent(
            EventType.BOOK_OVERDUE,
            String.format("Book '%s' is overdue (Due: %s)", 
                         record.getBookId(), record.getDueDate()),
            null, null, record, null
        );
    }
    
    public EventType getType() { return type; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Book getBook() { return book; }
    public User getUser() { return user; }
    public BorrowingRecord getBorrowingRecord() { return borrowingRecord; }
    public Object getAdditionalData() { return additionalData; }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s", timestamp, type, message);
    }
}
