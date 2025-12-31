/**
 * Custom Queue implementation for Borrow Requests.
 * Linked-list based implementation to avoid fixed size issues.
 */
public class Queue {
    // Helper class for Requests
    public static class Request {
        String userName;
        int bookId;
        Request next;

        public Request(String userName, int bookId) {
            this.userName = userName;
            this.bookId = bookId;
            this.next = null;
        }
    }

    private Request front, rear;

    // Time Complexity: O(1)
    public void enqueue(String userName, int bookId) {
        Request newRequest = new Request(userName, bookId);
        if (rear == null) {
            front = rear = newRequest;
            return;
        }
        rear.next = newRequest;
        rear = newRequest;
    }

    // Time Complexity: O(1)
    public Request dequeue() {
        if (front == null) return null;

        Request temp = front;
        front = front.next;

        if (front == null) rear = null;
        return temp;
    }

    // Time Complexity: O(1)
    public boolean isEmpty() {
        return front == null;
    }
}
