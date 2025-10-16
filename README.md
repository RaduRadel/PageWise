# PageWise

## Team
- BACCELA Radu-Costin  
- ȘTEFAN Mădălina-Eliana  

## Project Description
PageWise is an eLibrary management system designed to help readers and librarians manage books in an organized and efficient way.  
Readers can browse the catalog, borrow and return books, and keep track of due dates, while librarians can add, edit, or remove titles from the catalog and oversee borrowing activity.  

## Core Features

### 1. Book Catalog and Search
The catalog stores and displays all books available in the library.  
Users can search and filter by:
- Title, author, or genre  
- Availability  
- Sorting criteria such as alphabetical order or popularity  

The search logic uses interchangeable strategies for exact, partial, or keyword-based matches.

### 2. Borrowing and Returning System
Readers can borrow books directly from the catalog.  
Each borrowed book:
- Has a due date based on borrowing policies  
- Can be returned, updating its status automatically  
- Notifies other interested readers when it becomes available again  

This ensures accurate tracking of book circulation and availability.

### 3. Librarian Management
Librarians can:
- Add, edit, or remove books from the catalog  
- Manage stock and metadata  
- Monitor borrowing history and overdue titles  

Access control ensures that only librarians can modify catalog entries.

### 4. Notifications and Alerts
Readers receive notifications for:
- Upcoming due dates  
- Availability of reserved or previously unavailable books  

These are managed using an observer-based event notification system, which ensures that all subscribed users are updated automatically when relevant events occur.

## Design Patterns

### 1) Factory Method (Creational)
The system uses Factory Method to centralize how `Book` and `User` objects are created. Instead of instantiating them directly across the codebase (e.g., `new Book(...)`), a factory builds them with consistent defaults, validation, and unique IDs. This keeps creation logic in one place, reduces duplication, and allows new book or user types to be added without modifying code everywhere.  
**Why it fits PageWise:** PageWise manages multiple book and user types, and the Factory Method ensures consistent creation and easy extension.

---

### 2) Singleton (Creational)
Singleton ensures that global resources like the library catalog or notification center exist as a single instance. Instead of creating multiple copies or passing references everywhere, the system provides one shared instance that all components access. This prevents inconsistencies and simplifies management compared to manually coordinating shared objects.  
**Why it fits PageWise:** The catalog and notification center must remain consistent and accessible to all users.

---

### 3) Strategy (Behavioral)
Strategy is applied to searching and sorting the catalog. Readers may sort by title, author, or genre and search using exact, partial, or keyword matching. Each variant is implemented as a separate strategy that can be swapped at runtime, keeping the catalog logic clean. This is more maintainable and testable than scattering if/switch statements throughout services.  
**Why it fits PageWise:** Readers can choose different search and sort methods without changing the main catalog logic.

---

### 4) Observer (Behavioral)
Observer enables automatic notifications for events such as "book returned" or "due date approaching". Instead of directly calling every dependent component, the system publishes events that interested subscribers listen to. This decouples producers from consumers and is simpler to extend than manually updating all components whenever an event occurs.  
**Why it fits PageWise:** Readers receive timely updates automatically when relevant events occur, without tightly coupling components.
