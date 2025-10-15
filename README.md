# PageWise

## Team
- BACCELA Radu-Costin
- ȘTEFAN Mădălina-Eliana

## Project Description
PageWise is an eLibrary management system that helps readers and librarians manage books in a simple and organized way.
Users can browse the catalog, borrow and return books, and easily keep track of due dates.
Librarians can add or remove titles from the catalog.

## Core Features
- Book catalog with search and filter options
- Borrowing and returning system with due-date tracking
- Librarian management of titles
- Notifications for due dates and available books

## Design Patterns
### 1) Factory Method
The system uses Factory Method to centralize how `Book` objects are created. Instead of instantiating books directly across the codebase (`new Book(...)`), a factory builds them with consistent defaults and validation (e.g., normalized fields, generated IDs). This keeps creation logic in one place, reduces duplication, and makes future extensions possible without changing code everywhere.

### 2) Strategy
Strategy is applied to searching and sorting the catalog. Readers may sort by title, author, or genre and search using exact, partial, or keyword matching. Each variant is a separate strategy that can be swapped at runtime, keeping the catalog logic clean and open for extension. This is more maintainable and testable than scattering `if/switch` blocks throughout services.

### 3) Observer
Observer enables automatic notifications for events such as "book returned" and "due date approaching". Instead of directly calling every dependent component, the system publishes events that interested subscribers listen to.

### 4) Command
The Command pattern treats each user action (borrow, return, place hold) as its own small object with a single method like `execute()`. Instead of putting all steps in controllers, the command runs the action in one place, and an invoker (a simple CommandBus) triggers it.  