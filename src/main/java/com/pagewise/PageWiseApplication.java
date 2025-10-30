package com.pagewise;

import com.pagewise.database.Database;
import com.pagewise.factory.AbstractEntityFactory;
import com.pagewise.model.Book;
import com.pagewise.model.BorrowingRecord;
import com.pagewise.model.User;
import com.pagewise.observer.NotificationCenter;
import com.pagewise.observer.NotificationService;
import com.pagewise.service.CatalogService;
import com.pagewise.strategy.*;

public class PageWiseApplication {
    
    public static void main(String[] args) {
        PageWiseApplication app = new PageWiseApplication();
        app.demo();
    }
    
    private Database database;
    private CatalogService catalogService;
    private NotificationCenter notificationCenter;
    private NotificationService notificationService;

    public void demo() {
        database = Database.getInstance();
        database.clearAll();
        notificationCenter = new NotificationCenter();
        notificationService = new NotificationService(notificationCenter);
        notificationCenter.addObserver(notificationService);
        catalogService = new CatalogService();

        System.out.println("Database at start:");
        printDatabaseSummary();
        System.out.println();

        User user1 = AbstractEntityFactory.createUser("Demo Reader", "reader@email.com", User.UserType.READER);
        User user2 = AbstractEntityFactory.createUser("Demo Librarian", "lib@email.com", User.UserType.LIBRARIAN);
        Book book1 = AbstractEntityFactory.createBook("Alpha Book", "Zed Smith", "Fiction", "0000000000", java.time.LocalDate.of(2021,1,1), 2);
        Book book2 = AbstractEntityFactory.createBook("Beta Book", "Amy Wu", "Nonfiction", "1111111111", java.time.LocalDate.of(2022,2,2), 1);
        Book book3 = AbstractEntityFactory.createBook("Gamma Guide", "Nia Toth", "Reference", "2222222222", java.time.LocalDate.of(2023,3,3), 5);
        database.addUser(user1);
        database.addUser(user2);
        database.addBook(book1);
        database.addBook(book2);
        database.addBook(book3);
        System.out.println("Added 2 users, 3 books.");

        catalogService.setSearchStrategy(new PartialSearchStrategy());
        System.out.println("Search results for 'Book':");
        for (Book b : catalogService.searchBooks("Book")) {
            System.out.println(" - " + b.getTitle());
        }
        catalogService.setSortStrategy(new AuthorSortStrategy());
        System.out.println("Books sorted by author:");
        for (Book b : catalogService.sortBooks()) {
            System.out.println(" - " + b.getAuthor() + ": " + b.getTitle());
        }

        BorrowingRecord normalBorrow = new BorrowingRecord("BR1", user1.getId(), book1.getId(), java.time.LocalDate.now().minusDays(2), java.time.LocalDate.now().plusDays(5));
        BorrowingRecord overdueBorrow = new BorrowingRecord("BR2", user1.getId(), book2.getId(), java.time.LocalDate.now().minusDays(15), java.time.LocalDate.now().minusDays(1));
        database.addBorrowingRecord(normalBorrow);
        database.addBorrowingRecord(overdueBorrow);
        book1.borrowCopy();
        book2.borrowCopy();

        System.out.println("\nNotifications:");
        notificationService.checkDueDates(database.getAllBorrowingRecords(), 0);

        printDatabaseSummary();
        System.out.println("Borrowings:");
        for (BorrowingRecord br : database.getAllBorrowingRecords()) {
                System.out.println(br);
        }
    }

    /** Minimal DB summary output */
    private void printDatabaseSummary() {
        System.out.println("Books: " + database.getTotalBooks());
        for (Book b : database.getAllBooks()) {
            System.out.println("  " + b);
        }
        System.out.println("Users: " + database.getTotalUsers());
        for (User u : database.getAllUsers()) {
            System.out.println("  " + u);
        }
        System.out.println("Borrowings: " + database.getTotalBorrowingRecords());
    }
}
