/**
 * Represents a Book in the Library Management System.
 * This class stores all essential information about a book.
 */
public class Book {

    // Unique identifier of the book
    int id;

    // Title of the book (used as the key in the BST)
    String title;

    // Author name of the book
    String author;

    // Availability status of the book
    // true  -> book is available for borrowing
    // false -> book is currently borrowed
    boolean isAvailable;

    /**
     * Constructor to initialize a Book object.
     *
     * @param id          unique book ID
     * @param title       title of the book
     * @param author      author of the book
     * @param isAvailable availability status of the book
     */
    public Book(int id, String title, String author, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    /**
     * Returns a formatted string representation of the book.
     * This method is used when displaying book information
     * in the console (e.g., listing books or search results).
     */
    @Override
    public String toString() {
        return "ID: " + id +
               " | Title: " + title +
               " | Author: " + author +
               " | Status: " + (isAvailable ? "Available" : "Borrowed");
    }
}
