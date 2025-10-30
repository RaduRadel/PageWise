package com.pagewise.model;

import java.time.LocalDate;
import java.util.Objects;

public class BorrowingRecord {
    private String id;
    private String userId;
    private String bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private BorrowingStatus status;
    
    public enum BorrowingStatus {
        BORROWED, RETURNED, OVERDUE
    }
    
    public BorrowingRecord(String id, String userId, String bookId, LocalDate borrowDate, LocalDate dueDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = BorrowingStatus.BORROWED;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
    
    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }
    
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    
    public BorrowingStatus getStatus() { return status; }
    public void setStatus(BorrowingStatus status) { this.status = status; }
    
    public boolean isOverdue() {
        return status == BorrowingStatus.BORROWED && LocalDate.now().isAfter(dueDate);
    }
    
    public void markAsReturned() {
        this.returnDate = LocalDate.now();
        this.status = BorrowingStatus.RETURNED;
    }
    
    public void markAsOverdue() {
        if (status == BorrowingStatus.BORROWED) {
            this.status = BorrowingStatus.OVERDUE;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowingRecord that = (BorrowingRecord) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("BorrowingRecord{id='%s', userId='%s', bookId='%s', status=%s, dueDate=%s}", 
                           id, userId, bookId, status, dueDate);
    }
}
