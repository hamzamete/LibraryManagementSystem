/**
 * Represents a Book in the library.
 */
public class Book {
    int id;
    String title;
    String author;
    boolean isAvailable; // true if available, false if borrowed

    public Book(int id, String title, String author, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Title: " + title + " | Author: " + author + " | Status: " + (isAvailable ? "Available" : "Borrowed");
    }
}