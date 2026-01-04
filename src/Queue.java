/**
  * Custom Queue implementation for borrow requests.
  * This queue follows FIFO (First-In, First-Out) order.
  * A linked-list based structure is used to avoid fixed size limitations.
  */
public class Queue {

      /**
        * Inner class representing a single borrow request.
        * Each request stores:
        * - user name
        * - requested book ID
        * - reference to the next request in the queue
        */
      public static class Request {
            String userName;
            int bookId;
            Request next;

            // Creates a new borrow request
            public Request(String userName, int bookId) {
                  this.userName = userName;
                  this.bookId = bookId;
                  this.next = null;
            }
      }

      // Front points to the first request in the queue
      // Rear points to the last request in the queue
      private Request front, rear;

      /**
        * Adds a new borrow request to the end of the queue.
        * Time Complexity: O(1)
        */
      public void enqueue(String userName, int bookId) {
            Request newRequest = new Request(userName, bookId);

            // If the queue is empty, front and rear both point to the new request
            if (rear == null) {
                  front = rear = newRequest;
                  return;
            }

            // Otherwise, link the new request at the end and update rear
            rear.next = newRequest;
            rear = newRequest;
      }

      /**
        * Removes and returns the request at the front of the queue.
        * This represents processing the earliest borrow request.
        * Time Complexity: O(1)
        * @return the removed Request, or null if the queue is empty
        */
      public Request dequeue() {
            // If the queue is empty, nothing to dequeue
            if (front == null) return null;

            Request temp = front;
            front = front.next;

            // If the queue becomes empty after dequeue, update rear as well
            if (front == null)
                  rear = null;

            return temp;
      }

      /**
        * Checks whether the queue is empty.
        * Time Complexity: O(1)
        * @return true if the queue is empty, false otherwise
        */
      public boolean isEmpty() {
            return front == null;
      }

    /**
     * Displays all requests in the queue without removing them.
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        Request current = front;
        System.out.println("Current Borrow Requests:");
        while (current != null) {
            System.out.println("- User: " + current.userName + ", Book ID: " + current.bookId);
            current = current.next;
        }
    }
}
