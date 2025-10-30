package com.pagewise.observer;

import com.pagewise.model.Book;
import com.pagewise.model.BorrowingRecord;
import com.pagewise.model.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class NotificationService implements LibraryObserver {
    private final NotificationCenter notificationCenter;
    
    public NotificationService(NotificationCenter notificationCenter) {
        this.notificationCenter = notificationCenter;
    }
    
    @Override
    public void update(LibraryEvent event) {
        switch (event.getType()) {
            case BOOK_BORROWED:
                handleBookBorrowed(event);
                break;
            case BOOK_RETURNED:
                handleBookReturned(event);
                break;
            case BOOK_ADDED:
                handleBookAdded(event);
                break;
            case DUE_DATE_APPROACHING:
                handleDueDateApproaching(event);
                break;
            case BOOK_OVERDUE:
                handleBookOverdue(event);
                break;
            default:
                break;
        }
    }
    
    @Override
    public LibraryEvent.EventType[] getInterestedEventTypes() {
        return new LibraryEvent.EventType[]{
            LibraryEvent.EventType.BOOK_BORROWED,
            LibraryEvent.EventType.BOOK_RETURNED,
            LibraryEvent.EventType.BOOK_ADDED,
            LibraryEvent.EventType.DUE_DATE_APPROACHING,
            LibraryEvent.EventType.BOOK_OVERDUE
        };
    }
    
    private void handleBookBorrowed(LibraryEvent event) {
        System.out.println("NOTIFICATION: " + event.getMessage());
        System.out.println("   Due date: " + event.getBorrowingRecord().getDueDate());
    }
    
    private void handleBookReturned(LibraryEvent event) {
        System.out.println("NOTIFICATION: " + event.getMessage());
        System.out.println("   Book is now available for other readers");
    }
    
    private void handleBookAdded(LibraryEvent event) {
        System.out.println("NOTIFICATION: " + event.getMessage());
        System.out.println("   New book added to the catalog!");
    }
    
    private void handleDueDateApproaching(LibraryEvent event) {
        BorrowingRecord record = event.getBorrowingRecord();
        long daysUntilDue = ChronoUnit.DAYS.between(LocalDate.now(), record.getDueDate());
        
        System.out.println("REMINDER: " + event.getMessage());
        System.out.println("   Days until due: " + daysUntilDue);
    }
    
    private void handleBookOverdue(LibraryEvent event) {
        BorrowingRecord record = event.getBorrowingRecord();
        long daysOverdue = ChronoUnit.DAYS.between(record.getDueDate(), LocalDate.now());
        
        System.out.println("OVERDUE NOTICE: " + event.getMessage());
        System.out.println("   Days overdue: " + daysOverdue);
        System.out.println("   Please return the book as soon as possible!");
    }
    
    public void checkDueDates(List<BorrowingRecord> borrowingRecords, int daysThreshold) {
        LocalDate today = LocalDate.now();
        
        for (BorrowingRecord record : borrowingRecords) {
            if (record.getStatus() == BorrowingRecord.BorrowingStatus.BORROWED) {
                long daysUntilDue = ChronoUnit.DAYS.between(today, record.getDueDate());
                
                if (daysUntilDue <= daysThreshold && daysUntilDue >= 0) {
                    LibraryEvent event = LibraryEvent.dueDateApproaching(record);
                    notificationCenter.notifyObservers(event);
                } else if (daysUntilDue < 0) {
                    LibraryEvent event = LibraryEvent.bookOverdue(record);
                    notificationCenter.notifyObservers(event);
                }
            }
        }
    }
}
