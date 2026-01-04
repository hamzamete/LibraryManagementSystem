/**
 * Custom Stack implementation used for undo operations.
 * This stack follows LIFO (Last-In, First-Out) order.
 */
public class Stack {

    /**
     * Defines the type of action that can be undone.
     * BORROW  -> a book was borrowed
     * RETURN  -> a book was returned
     */
    public enum ActionType {
        BORROW, RETURN
    }

    /**
     * Inner class representing a single action in the stack.
     * Each action stores:
     * - the action type (BORROW or RETURN)
     * - the related book ID
     * - a reference to the next action in the stack
     */
    public static class Action {
        ActionType type;
        int bookId;
        Action next;

        // Creates a new undo action
        public Action(ActionType type, int bookId) {
            this.type = type;
            this.bookId = bookId;
        }
    }

    // Top of the stack (most recent action)
    private Action top;

    /**
     * Pushes a new action onto the stack.
     * This records the most recent borrow or return operation.
     *
     * Time Complexity: O(1)
     */
    public void push(ActionType type, int bookId) {
        Action newAction = new Action(type, bookId);
        newAction.next = top;
        top = newAction;
    }

    /**
     * Removes and returns the most recent action from the stack.
     * This is used to undo the last operation.
     *
     * Time Complexity: O(1)
     *
     * @return the last Action, or null if the stack is empty
     */
    public Action pop() {
        if (top == null) return null;

        Action temp = top;
        top = top.next;
        return temp;
    }

    /**
     * Checks whether the stack is empty.
     *
     * Time Complexity: O(1)
     *
     * @return true if the stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return top == null;
    }
}
