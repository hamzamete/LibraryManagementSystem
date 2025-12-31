import java.io.*;
import java.util.Scanner;

public class Library {
    private DynamicArray catalog;
    private BST titleSearchTree;
    private Queue borrowRequests;
    private Stack undoStack;

    // New fields for User Management
    private String[] registeredUsers;
    private int userCount;

    private final String BOOK_FILE = "books.txt";
    private final String USER_FILE = "users.txt";

    public Library() {
        catalog = new DynamicArray(5); // Start small to test resizing
        titleSearchTree = new BST();
        borrowRequests = new Queue();
        undoStack = new Stack();

        // Initialize user storage (Simple array for demo purposes)
        registeredUsers = new String[100];
        userCount = 0;

        loadBooks();
        loadUsers(); // Load users from txt file
    }

    // 1. Add Book
    public void addBook(int id, String title, String author) {
        Book newBook = new Book(id, title, author, true);
        catalog.add(newBook);
        titleSearchTree.insert(newBook);
        System.out.println("Book added successfully.");
    }

    // 2. Remove Book
    public void removeBook(int id) {
        int index = catalog.findIndexById(id);

        if (index != -1) {
            Book b = catalog.get(index);
            catalog.remove(index);
            System.out.println("Book removed successfully: " + b.title);
        } else {
            System.out.println("Book with ID " + id + " not found.");
        }
    }

    // 3. Search by ID
    public void searchById(int id) {
        Book b = catalog.findById(id);
        if (b != null) System.out.println(b);
        else System.out.println("Book not found.");
    }

    // 4. Search by Title
    public void searchByTitle(String title) {
        Book b = titleSearchTree.search(title);
        if (b != null) System.out.println(b);
        else System.out.println("Book not found in BST.");
    }

    // 5. List all (Array order)
    public void listAllBooks() {
        for (int i = 0; i < catalog.size(); i++) {
            System.out.println(catalog.get(i));
        }
    }

    // 6. List all (Alphabetical - BST)
    public void listBooksAlphabetically() {
        titleSearchTree.inOrderTraversal();
    }

    // 7. Request Borrow (Updated to check if user exists)
    public void requestBorrow(String user, int bookId) {
        if (isUserRegistered(user)) {
            borrowRequests.enqueue(user, bookId);
            System.out.println("Request added to queue for user: " + user);
        } else {
            System.out.println("Error: User '" + user + "' is not registered in users.txt! Cannot add request.");
        }
    }

    // 8. Process Requests
    public void processRequests() {
        Queue.Request req = borrowRequests.dequeue();
        if (req == null) {
            System.out.println("No waiting requests.");
            return;
        }
        System.out.println("Processing request for User: " + req.userName + ", BookID: " + req.bookId);
        borrowBook(req.bookId);
    }

    // 9. Borrow Book
    public void borrowBook(int id) {
        Book b = catalog.findById(id);
        if (b != null && b.isAvailable) {
            b.isAvailable = false;
            undoStack.push(Stack.ActionType.BORROW, id);
            System.out.println("Book borrowed: " + b.title);
        } else {
            System.out.println("Book not available or not found.");
        }
    }

    // 10. Return Book
    public void returnBook(int id) {
        Book b = catalog.findById(id);
        if (b != null && !b.isAvailable) {
            b.isAvailable = true;
            undoStack.push(Stack.ActionType.RETURN, id);
            System.out.println("Book returned: " + b.title);
        } else {
            System.out.println("Book was not borrowed.");
        }
    }

    // 11. Undo
    public void undoLastAction() {
        Stack.Action lastAction = undoStack.pop();
        if (lastAction == null) {
            System.out.println("Nothing to undo.");
            return;
        }

        Book b = catalog.findById(lastAction.bookId);
        if (b == null) return;

        // Revert the action
        if (lastAction.type == Stack.ActionType.BORROW) {
            // Undo borrow means return it (make available)
            b.isAvailable = true;
            System.out.println("Undo: Borrow action reverted. Book is now available.");
        } else if (lastAction.type == Stack.ActionType.RETURN) {
            // Undo return means borrow it back (make unavailable)
            b.isAvailable = false;
            System.out.println("Undo: Return action reverted. Book is now borrowed.");
        }
    }

    // File I/O: Load Books
    private void loadBooks() {
        try (Scanner scanner = new Scanner(new File(BOOK_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(","); // Format: ID,Title,Author,Status
                if (parts.length >= 3) {
                    int id = Integer.parseInt(parts[0].trim());
                    String title = parts[1].trim();
                    String author = parts[2].trim();
                    boolean status = Boolean.parseBoolean(parts[3].trim());

                    Book b = new Book(id, title, author, status);
                    catalog.add(b);
                    titleSearchTree.insert(b);
                }
            }
            System.out.println("Books loaded from file.");
        } catch (FileNotFoundException e) {
            System.out.println("No book file found. Starting fresh.");
        }
    }

    // File I/O: Load Users (New Method)
    private void loadUsers() {
        try (Scanner scanner = new Scanner(new File(USER_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                // Ensure we don't exceed array size and ignore empty lines
                if (!line.isEmpty() && userCount < registeredUsers.length) {
                    registeredUsers[userCount] = line;
                    userCount++;
                }
            }
            System.out.println("Users loaded from file.");
        } catch (FileNotFoundException e) {
            System.out.println("No user file found. No registered users.");
        }
    }

    // Helper: Check if user exists
    private boolean isUserRegistered(String name) {
        for (int i = 0; i < userCount; i++) {
            if (registeredUsers[i].equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}