/**
 * The task is to choose one of the implementation that was created in the previous assignments. Then after
 * continue to build upon it to create an ordered queue which sorts the data at insertion. The data in this
 * case is integer values.
 */

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        OrderedQueue<Integer> q1 = new OrderedQueue<>();
        OrderedQueue<Integer> q2 = new OrderedQueue<>();
        OrderedQueue<Integer> q3 = new OrderedQueue<>();
        // Test 1
        q1.enqueue(10);      // [10]
        q1.enqueue(8);       // [8],[10]
        q1.enqueue(5);       // [5],[8],[10]
        q1.enqueue(88);      // [5],[8],[10],[88]
        q1.enqueue(1);       // [1],[5],[8],[10],[88]
        q1.enqueue(13);      // [1],[5],[8],[10],[13],[88]
        q1.dequeue();             // [5],[8],[10],[13],[88]
        q1.dequeue();             // [8],[10],[13],[88]
        System.out.println("");   // Blank line.

        // Test 2
        q2.enqueue(-10);     // [-10]
        q2.enqueue(-3);      // [-10],[-3]
        q2.enqueue(-7);      // [-10],[-7],[-3]
        q2.enqueue(4);       // [-10],[-7],[-3],[4]
        q2.enqueue(0);       // [-10],[-7],[-3],[0],[4]
        System.out.println("");   // Blank line.

        //Test 3
        q3.enqueue(18);      // [18]
        q3.enqueue(25);      // [18],[25]
        q3.enqueue(11);      // [11],[18],[25]
        q3.dequeue();             // [18],[25]
        q3.dequeue();             // [25]
        q3.dequeue();             // []
        q3.dequeue();             // Error message.
    }
}

/**
 * the list of data to store in FIFO(first in first out) fashion in other words double linked circular list.
 * @param <Item> the object type the list will consist of.
 */
class OrderedQueue<Item extends Comparable<Item>> implements Iterable<Item>{
    private Node<Item> tail; // Node to reference the first element in the list.
    private Node<Item> head; // Node to reference the last recent added element.
    private int size;

    /**
     * Constructor.
     */
    OrderedQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Add the item to the queue list.
     * @param item the item that will be added to the list.
     */
    void enqueue(Item item) {
        if(isEmpty()) { // The list is empty [] so fill the tail node.
            this.tail = new Node<Item>(item);
            this.head = new Node<Item>(item);
            this.tail.setNextNode(this.head);
            this.tail.setPreviousNode(this.head);
            this.head.setNextNode(this.tail);
            this.head.setPreviousNode(this.tail);
        } else if(this.size == 1) { // The list consist of 1 element thus update the one of the nodes (head and tail).
            if(item.compareTo(this.tail.getItem()) <= 0) { // Edge case (tail).
                this.head = this.tail;
                this.tail = new Node<Item>(item);
                this.head.setPreviousNode(this.tail);
                this.head.setNextNode(this.tail);
                this.tail.setPreviousNode(this.head);
                this.tail.setNextNode(this.head);
            } else { // // Edge case (head).
                this.head = new Node<Item>(item);
                this.tail.setNextNode(this.head);
                this.tail.setPreviousNode(this.head);
                this.head.setNextNode(this.tail);
                this.head.setPreviousNode(this.tail);
            }
        } else if(item.compareTo(this.tail.getItem()) <= 0) { // Edge case (tail).
            Node<Item> oldTail = this.tail;
            this.tail = new Node<Item>(item);
            oldTail.setPreviousNode(this.tail);
            this.tail.setNextNode(oldTail);
            this.tail.setPreviousNode(this.head);
        } else if(item.compareTo(this.head.getItem()) > 0) { // Edge case (head).
            Node<Item> oldHead = this.head;
            this.head = new Node<Item>(item);
            this.head.setNextNode(this.tail);
            this.head.setPreviousNode(oldHead);
            oldHead.setNextNode(this.head);
        } else { // No special case present thus insert in the right place.
            Node<Item> current = this.tail.getNextNode();
            for(int i = 0; i < size; i++) {
                if(item.compareTo(current.getItem()) <= 0) {
                    Node<Item> temp = new Node<>(item);
                    current.getPreviousNode().setNextNode(temp);
                    temp.setPreviousNode(current.getPreviousNode());
                    temp.setNextNode(current);
                    current.setPreviousNode(temp);
                    break;
                } else {
                    current = current.getNextNode();
                }
            }
        }
        this.size++;
        System.out.println(toString());
    }

    /**
     * Take out the first added item to the list.
     * @return the item that will be removed.
     */
    Item dequeue() {
        if(isEmpty()) { // It is not possible to remove and element if the list is already empty.
            this.size = -1;
            System.out.println(toString());
            return null;
        } else if(this.size == 1) { // One element is present in the list in which the head and tail nodes.
            Item temp = this.tail.getItem();
            this.tail = null;
            this.head = null;
            this.size--;
            System.out.println(toString());
            return temp;
        } else { // No special case is present so remove the first element in the list.
            Item temp  = this.tail.getItem();
            this.tail = this.tail.getNextNode();
            this.tail.setPreviousNode(this.head);
            this.head.setNextNode(this.tail);
            this.size--;
            System.out.println(toString());
            return temp;
        }
    }

    /**
     * Check whether the list is empty or not.
     * @return true or false depending on the condition.
     */
    boolean isEmpty() {
        return this.tail == null;
    }

    /**
     * Gives the size of the list.
     * @return the count of the items in the list.
     */
    int getSize() {
        return this.size;
    }

    /**
     * Represent the list in a specific style ([x],[x]...).
     * @return string containing the elements of the list.
     */
    @Override
    public String toString() {
        String output = "";
        Iterator<Item> iterator = iterator();
        for(int i = 0; i < size; i++) {
            output += ",[" + iterator.next() + "]";
        }
        if(size == 0) {
            output += "[]";
        } else if(size < 0) {
            output += "Unable to remove any element because the stack is already empty.";
        }
        return output.replaceFirst(",", "");
    }

    /**
     * Create an instance of the iterator for the queue.
     * @return the queue instance.
     */
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    /**
     * The iterator implementation to loop through the elements of the list.
     * Inspired from the book "Algorithms 4th Edition by Robert Sedgewick, Kevin Wayne" p.155.
     */
    class QueueIterator implements Iterator<Item> {
        Node<Item> current = tail;

        /**
         * Check whether there is an elements existent after the current one.
         * @return true or false depending on the situation.
         */
        public boolean hasNext() {
            return this.current != null;
        }

        /**
         * Get the the current element and update it for the next one.
         * @return the current element.
         */
        public Item next() {
            Item item = current.getItem();
            current = current.getNextNode();
            return item;
        }
    }
}

/**
 * The container holding the element and reference the next element that comes after.
 * @param <Item> the type of element.
 */
class Node<Item> {
    private Item item; // Hold the value.
    private Node<Item> nextNode; // Reference for the node that is next in line.
    private Node<Item> previousNode; // Reference for the node that is previous in line.

    /**
     * Constructor.
     * @param item the element to store.
     */
    Node(Item item) {
        this.item = item;
        this.nextNode = null;
        this.previousNode = null;
    }

    /**
     * Update the next node that comes after.
     * @param newNode the next node.
     */
    void setNextNode(Node<Item> newNode) {
        this.nextNode = newNode;
    }

    /**
     * Update the previous node with what comes before.
     * @param newNode the previous node.
     */
    void setPreviousNode(Node<Item> newNode) {
        this.previousNode = newNode;
    }

    /**
     * Gives the next node.
     * @return the next node.
     */
    Node<Item> getNextNode() {
        return this.nextNode;
    }

    /**
     * Gives the previous node.
     * @return the previous node.
     */
    Node<Item> getPreviousNode() {
        return this.previousNode;
    }

    /**
     * Gives the item value.
     * @return the item value.
     */
    Item getItem() {
        return this.item;
    }

    /**
     * Check whether there is next node or not.
     * @return true or false depending on the condition.
     */
    boolean hasNext() {
        return this.nextNode == null;
    }

    /**
     * Checks whether there is a previous node or not.
     * @return true or false depending on the condition.
     */
    boolean hasPrevious() {
        return this.previousNode == null;
    }
}
