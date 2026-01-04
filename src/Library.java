import java.io.*;
import java.util.Scanner;

/**
 * Library class is the core manager of the Library Management System.
 * It coordinates all data structures:
 * - DynamicArray for catalog storage
 * - BST for title-based search and alphabetical listing
 * - Queue for borrow requests (FIFO)
 * - Stack for undo operations (LIFO)
 */
public class Library {

    // Stores all books in insertion order
    private DynamicArray catalog = new DynamicArray(5);

    // Stores books for fast title-based search and alphabetical ordering
    private BST bst = new BST();

    // Stores borrow requests in FIFO order
    private Queue queue = new Queue();

    // Stores borrow/return actions for undo functionality
    private Stack stack = new Stack();

    // File used to persist book data
    private final String BOOK_FILE = "books.txt";

    /**
     * Constructor.
     * Loads books from file into the catalog and BST when the system starts.
     *
     * Worst-case time complexity: O(n^2)
     * (n book insertions into BST, each insert can be O(n) in worst case)
     */
    public Library() {
        loadBooks();
    }

    /**
     * Adds a new book to the library.
     * - Adds to DynamicArray for catalog management
     * - Inserts into BST for title search and alphabetical listing
     * - Saves updated catalog to file
     *
     * Worst-case time complexity: O(n)
     */
    public void addBook(int id, String title, String author) {
        Book b = new Book(id, title, author, true);
        catalog.add(b);
        bst.insert(b);
        saveBooks();
    }

    /**
     * Removes a book from the library using its ID.
     * - Finds and removes the book from DynamicArray
     * - Deletes the same book from BST using its title
     * - Saves updated catalog to file
     *
     * Worst-case time complexity: O(n)
     */
    public void removeBook(int id) {
        int index = catalog.findIndexById(id);
        if (index == -1) return;

        Book b = catalog.get(index);
        catalog.remove(index);
        bst.delete(b.title);
        saveBooks();
    }

    /**
     * Searches for a book by its ID using DynamicArray.
     *
     * Worst-case time complexity: O(n)
     */
    public void searchById(int id) {
        Book b = catalog.findById(id);
        System.out.println(b == null ? "Not found" : b);
    }

    /**
     * Searches for a book by its title using BST.
     *
     * Worst-case time complexity: O(n)
     */
    public void searchByTitle(String title) {
        Book b = bst.search(title);
        System.out.println(b == null ? "Not found" : b);
    }

    /**
     * Lists all books in the order they were added.
     * Uses DynamicArray to preserve insertion order.
     *
     * Worst-case time complexity: O(n)
     */
    public void listAll() {
        for (int i = 0; i < catalog.size(); i++)
            System.out.println(catalog.get(i));
    }

    /**
     * Lists all books in alphabetical order by title.
     * Uses BST in-order traversal.
     *
     * Worst-case time complexity: O(n)
     */
    public void listAlpha() {
        bst.inOrderTraversal();
    }

    /**
     * Adds a borrow request to the waiting list.
     * Queue ensures FIFO (first come, first served).
     *
     * Worst-case time complexity: O(1)
     */
    public void requestBorrow(String user, int id) {
        queue.enqueue(user, id);
    }

    /**
     * Processes the earliest borrow request from the queue.
     * - If the book does not exist, the request is discarded
     * - If the book is available, it is borrowed
     * - If the book is not available, the request is re-enqueued
     *
     * Worst-case time complexity: O(n)
     */
    public void processRequests() {
        Queue.Request r = queue.dequeue();
        if (r == null) return;

        Book b = catalog.findById(r.bookId);

        // If the requested book does not exist, ignore the request
        if (b == null) {
            System.out.println("Request ignored: Book ID " + r.bookId + " not found.");
            return;
        }

        // If available, borrow the book
        if (b.isAvailable) {
            borrowBook(r.bookId);
        } else {
            // Otherwise, place the request back at the end of the queue
            queue.enqueue(r.userName, r.bookId);
        }
    }

    /**
     * Borrows a book.
     * - Marks the book as unavailable
     * - Pushes the action onto the stack for undo
     * - Saves updated catalog to file
     *
     * Worst-case time complexity: O(n)
     */
    public void borrowBook(int id) {
        Book b = catalog.findById(id);
        if (b != null && b.isAvailable) {
            b.isAvailable = false;
            stack.push(Stack.ActionType.BORROW, id);
            saveBooks();
        }
    }

    /**
     * Returns a book.
     * - Marks the book as available
     * - Pushes the action onto the stack for undo
     * - Saves updated catalog to file
     *
     * Worst-case time complexity: O(n)
     */
    public void returnBook(int id) {
        Book b = catalog.findById(id);
        if (b != null && !b.isAvailable) {
            b.isAvailable = true;
            stack.push(Stack.ActionType.RETURN, id);
            saveBooks();
        }
    }

    /**
     * Undoes the last borrow or return action.
     * Uses Stack (LIFO) to revert the most recent operation.
     *
     * - If last action was BORROW → book becomes available
     * - If last action was RETURN → book becomes borrowed
     *
     * Worst-case time complexity: O(n)
     */
    public void undo() {
        Stack.Action a = stack.pop();
        if (a == null) return;

        Book b = catalog.findById(a.bookId);
        if (b == null) return;

        // Revert the last action explicitly
        if (a.type == Stack.ActionType.BORROW) {
            b.isAvailable = true;
        } else if (a.type == Stack.ActionType.RETURN) {
            b.isAvailable = false;
        }

        saveBooks();
    }

    /**
     * Loads books from the text file.
     * Each line format: id,title,author,isAvailable
     * Loaded books are added to both DynamicArray and BST.
     *
     * Worst-case time complexity: O(n^2)
     */
    private void loadBooks() {
        try (Scanner sc = new Scanner(new File(BOOK_FILE))) {
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                Book b = new Book(
                        Integer.parseInt(p[0]),
                        p[1],
                        p[2],
                        Boolean.parseBoolean(p[3])
                );
                catalog.add(b);
                bst.insert(b);
            }
        } catch (Exception e) {
            // If the file does not exist, the library starts empty.
        }
    }

    /**
     * Saves all books in the catalog to the text file.
     * The entire file is overwritten each time.
     *
     * Worst-case time complexity: O(n)
     */
    private void saveBooks() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOK_FILE))) {
            for (int i = 0; i < catalog.size(); i++) {
                Book b = catalog.get(i);
                pw.println(b.id + "," + b.title + "," + b.author + "," + b.isAvailable);
            }
        } catch (Exception e) {
            // If saving fails, changes may not persist.
        }
    }
}
