/**
 * Custom implementation of a Dynamic Array for storing Book objects.
 * This class mimics the behavior of Java's ArrayList but is implemented
 * manually to understand dynamic resizing.
 */
public class DynamicArray {

    // Internal array that stores Book objects
    private Book[] books;

    // Number of elements currently stored in the array
    private int size;

    // Current capacity of the internal array
    private int capacity;

    /**
     * Constructor that initializes the dynamic array
     * with a given initial capacity.
     *
     * @param initialCapacity initial size of the array
     */
    public DynamicArray(int initialCapacity) {
        this.capacity = initialCapacity;
        this.books = new Book[capacity];
        this.size = 0;
    }

    /**
     * Adds a new Book to the dynamic array.
     * If the array is full, its capacity is doubled.
     *
     * Time Complexity:
     * Amortized: O(1)
     * Worst case: O(n) when resizing occurs
     */
    public void add(Book book) {
        if (size == capacity) {
            resize();
        }
        books[size] = book;
        size++;
    }

    /**
     * Resizes the internal array by doubling its capacity.
     * Copies all existing elements to the new array.
     *
     * Time Complexity:
     * O(n), where n is the number of stored elements
     */
    private void resize() {
        capacity *= 2;
        Book[] newBooks = new Book[capacity];
        for (int i = 0; i < size; i++) {
            newBooks[i] = books[i];
        }
        books = newBooks;
    }

    /**
     * Returns the Book at the specified index.
     *
     * Time Complexity:
     * O(1)
     *
     * @param index index of the requested element
     * @return Book at the given index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public Book get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return books[index];
    }

    /**
     * Removes the Book at the specified index.
     * Elements to the right are shifted left.
     *
     * Time Complexity:
     * O(n), due to element shifting
     *
     * @param index index of the element to remove
     */
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        for (int i = index; i < size - 1; i++) {
            books[i] = books[i + 1];
        }
        books[size - 1] = null; // Helps garbage collection
        size--;
    }

    /**
     * Searches for a book by its ID using linear search.
     *
     * Time Complexity:
     * O(n)
     *
     * @param id book ID to search for
     * @return Book if found, otherwise null
     */
    public Book findById(int id) {
        for (int i = 0; i < size; i++) {
            if (books[i].id == id) {
                return books[i];
            }
        }
        return null;
    }

    /**
     * Returns the number of elements currently stored
     * in the dynamic array.
     *
     * Time Complexity:
     * O(1)
     */
    public int size() {
        return size;
    }

    /**
     * Finds the index of a book by its ID.
     *
     * Time Complexity:
     * O(n)
     *
     * @param id book ID to search for
     * @return index if found, otherwise -1
     */
    public int findIndexById(int id) {
        for (int i = 0; i < size; i++) {
            if (books[i].id == id) {
                return i;
            }
        }
        return -1; // Not found
    }
}
