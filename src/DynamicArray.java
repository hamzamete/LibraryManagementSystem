/**
 * Custom implementation of a Dynamic Array.
 * It resizes itself when full.
 */
public class DynamicArray {
    private Book[] books;
    private int size;
    private int capacity;

    public DynamicArray(int initialCapacity) {
        this.capacity = initialCapacity;
        this.books = new Book[capacity];
        this.size = 0;
    }

    // Time Complexity: O(1) amortized, O(n) worst-case (when resizing)
    public void add(Book book) {
        if (size == capacity) {
            resize();
        }
        books[size] = book;
        size++;
    }

    // Time Complexity: O(n) - involves copying elements
    private void resize() {
        capacity *= 2;
        Book[] newBooks = new Book[capacity];
        for (int i = 0; i < size; i++) {
            newBooks[i] = books[i];
        }
        books = newBooks;
    }

    // Time Complexity: O(1)
    public Book get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return books[index];
    }

    // Time Complexity: O(n) - shifting elements required
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        for (int i = index; i < size - 1; i++) {
            books[i] = books[i + 1];
        }
        books[size - 1] = null; // Help GC
        size--;
    }

    // Time Complexity: O(n) - linear search
    public Book findById(int id) {
        for (int i = 0; i < size; i++) {
            if (books[i].id == id) {
                return books[i];
            }
        }
        return null;
    }

    // Time Complexity: O(1)
    public int size() {
        return size;
    }

    // Time Complexity: O(n) - Linear search to find index
    public int findIndexById(int id) {
        for (int i = 0; i < size; i++) {
            if (books[i].id == id) {
                return i;
            }
        }
        return -1; // Not found
    }
}