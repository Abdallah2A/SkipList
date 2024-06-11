import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * This class implements SkipList data structure and contains an inner SkipNode
 * class which the SkipList will make an array of to store data.
 * 
 * @author {Abdallah Ali Hassan}
 * 
 * @version 2021-08-23
 * @param <K>
 *            Key
 * @param <V>
 *            Value
 */
public class SkipList<K extends Comparable<? super K>, V>
    implements Iterable<KVPair<K, V>> {
    private SkipNode head; // First element of the top level
    private int size; // number of entries in the Skip List
    
	
    /**
     * Initializes the fields head, size and level
     */
    public SkipList() {
        head = new SkipNode(null, 0);
        size = 0;
    }


    /**
     * Returns a random level number which is used as the depth of the SkipNode
     * 
     * @return a random level number
     */
    int randomLevel() {
        int lev;
        Random value = new Random();
        for (lev = 0; Math.abs(value.nextInt()) % 2 == 0; lev++) {
            // Do nothing
        }
        return lev; // returns a random level
    }


    /**
     * Searches for the KVPair using the key which is a Comparable object.
     * 
     * @param key
     *            key to be searched for
     * @return An ArrayList of KVPair(s) associated with the given key if found,
     *		   otherwise an empty ArrayList is returned
     */
    public ArrayList<KVPair<K, V>> search(K key) {
    	// Create an ArrayList to carry all found KVPair objects
        ArrayList<KVPair<K, V>> foundPairs = new ArrayList<>();
        // Start the search at the head of the skip list
        SkipNode currentNode = head;

        // Starting from the highest level and move downwards to find the node with the given key
        for (int i = head.level; i >= 0; i--) {
            // Move the current pointer to the right until the next KVPair's key is greater than or equal to the given key
            while (currentNode.forward[i] != null && currentNode.forward[i].element().getKey().compareTo(key) < 0) {
            	currentNode = currentNode.forward[i];
            }
        }
        
        // Move to actual node
        currentNode = currentNode.forward[0];
        
        // Add all found KVPair objects with the given key to the foundNodes ArrayList
        while (currentNode != null && currentNode.element().getKey().compareTo(key) == 0) {
            KVPair<K, V> foundPair = currentNode.element();
            foundPairs.add(foundPair);
        	currentNode = currentNode.forward[0];
        }
        
        return foundPairs;
    }


    /**
     * @return the size of the SkipList
     */
    public int size() {
        return size;
    }    


    /**
     * Inserts the KVPair in the SkipList at its appropriate spot as designated
     * by its lexicoragraphical order.
     * 
     * @param it
     *            the KVPair to be inserted
     */
    @SuppressWarnings("unchecked")
    public void insert(KVPair<K, V> it) {
    	// Create random new level for new node
        int newLevel = randomLevel();
        
        // Extract the key from the given key-value pair
        K key = it.getKey();
        
        // Increase the level of the head node if necessary
        if (newLevel > head.level) {
            adjustHead(newLevel);
        }
        
        // Create a new node with the given KVpair and level
        SkipNode newNode = new SkipNode(it, newLevel);
        
        // Search for the position to insert the new node
        // Start the search at the head of the skip list
        SkipNode currentNode = head;
        
        // Create an array to keep track of the positions of the nodes that are visited
        SkipNode[] tempForward = (SkipList<K, V>.SkipNode[]) Array.newInstance(SkipNode.class, head.level+1);
        
        // Starting from the highest level and move downwards to find the node with the given key
        for (int i = head.level; i >= 0; i--) {
            // Move the current pointer to the right until the next KVPair's key is greater than or equal to the given key
            while (currentNode.forward[i] != null && currentNode.forward[i].element().getKey().compareTo(key) < 0) {
            	currentNode = currentNode.forward[i];
            }
            tempForward[i] = currentNode;
        }
        
        // Move to actual node
        currentNode = currentNode.forward[0];

        // Put the new node in the write position
        for (int i = 0; i <= newLevel; i++) {
            newNode.forward[i] = tempForward[i].forward[i];
            tempForward[i].forward[i] = newNode;
        }
        
        // Increasing the skip list size by one after insert
        size++;
    }


    /**
     * Increases the number of levels in head so that no element has more
     * indices than the head.
     * 
     * @param newLevel
     *            the number of levels to be added to head
     */
    @SuppressWarnings("unchecked")
    private void adjustHead(int newLevel) {
    	// Creat new node for head with new level
        SkipNode newHead = new SkipNode(null, newLevel);
        
        // Connect new head with the skip list
        for (int i = 0; i <= head.level; i++) {
            newHead.forward[i] = head.forward[i];
        }
        // Change the head to the new one
        head = newHead;
    }


    /**
     * Removes the KVPair that is passed in as a parameter
     * 
     * @param key
     *            the key of the KVPair to be removed
     * @return returns the removed pair if the pair was found and null if not
     */

    @SuppressWarnings("unchecked")
    public KVPair<K, V> remove(K key) {
        // Start the search at the head of the skip list
        SkipNode currentNode = head;
        
        // Create an array to keep track of the positions of the nodes that are visited
        SkipNode[] tempForward = (SkipList<K, V>.SkipNode[]) Array.newInstance(SkipNode.class, head.level+1 ); 
        
        // Search for the position to remove the node
        // Starting from the highest level and move downwards to find the node with the given key
        for (int i = head.level; i >= 0; i--) {
            // Move the current pointer to the right until the next KVPair's key is greater than or equal to the given key
            while (currentNode.forward[i] != null && currentNode.forward[i].element().getKey().compareTo(key) < 0) {
            	currentNode = currentNode.forward[i];
            }
            tempForward[i] = currentNode;
        }
        
        // Move to actual node
        currentNode = currentNode.forward[0];
        
        // Checking if found the node needs to remove
        if (currentNode != null && currentNode.element().getKey().compareTo(key) == 0) {
        	KVPair<K,V> removePair = currentNode.element();
        	
        	// Removing the node by updating the forward pointers of the nodes that pointed to it
            for (int i = 0; i <= head.level; i++) {
            	// This if statment to end loop after connect all levels of previous node with next node
                if (tempForward[i].forward[i] != currentNode) {
                    break;
                }
                tempForward[i].forward[i] = currentNode.forward[i];
            }
            
            // decreasing the skip list size by one after remove
            size--;
            
            return removePair;
        }
        // Return null if node not found
        return null;
    }


    /**
     * Removes a KVPair with the specified value.
     * 
     * @param val
     *            the value of the KVPair to be removed
     * @return returns the removed pair if the pair was found and null if not
     */

    public KVPair<K, V> removeByValue(V val) {
        // Start the search at the head of the skip list
        SkipNode currentNode = head;
        
        // Go for every node one by one
        while (currentNode.forward[0] != null) {
        	// Take the value and key for every pair to compare
            V currentNodeValue = currentNode.forward[0].element().getValue();
            K currentNodeKey = currentNode.forward[0].element().getKey();
            
            // Check if found pair or not using the value
            if (currentNodeValue != null && currentNodeValue.equals(val)) {
            	// Remove the pair by using remove(key) method
            	KVPair<K,V> removePair = remove(currentNodeKey);
                return removePair;
            }
            
            currentNode = currentNode.forward[0];
        }
        
        // Return null if the pair not found
        return null;
    }



    /**
     * Prints out the SkipList in a human readable format to the console.
     */
    public void dump() {
        // Print the header for the skip list dump
        System.out.println("SkipList dump:");
        // Start at the head of the skip list
        SkipNode currentNode = head;
        
        // Loop through each node in the skip list
        while (currentNode != null) {
            // Extract the value from the key-value pair of the current node
        	String value = null;
        	
        	if (currentNode.element() != null) {
        	value = currentNode.element().getValue().toString().replaceAll("java.awt.Rectangle\\[x=|y=|width=|height=|\\]", "");
        	value = "("+ currentNode.element().getKey() + " ," +  value.toString().replaceAll(",", ", ") + ")";
        	}
        	
        	else {
        		value = "(null)";
        	}
        	
            // Print the depth and value of the current node
            System.out.println("Node has depth "+ currentNode.level + ", Value " + value);
            // Move to the next node
            currentNode = currentNode.forward[0];
        }
        
        // Print the size of the skip list
        System.out.println("SkipList size is: " + size);
    }

    
    /**
     * This class implements a SkipNode for the SkipList data structure.
     * 
     * @author CS Staff
     * 
     * @version 2016-01-30
     */
    private class SkipNode {

        // the KVPair to hold
        private KVPair<K, V> pair;
        // what is this
        // The array forward in the SkipNode class is used to store references to the next SkipNode objects in the Skip List.
        // The size of this array is determined by the level of the current node.
        // Each element of the array stores a reference to the next node at the corresponding level,
        // with forward[0] being the next node at level 0 and forward[level] being the next node at the highest level
        private SkipNode [] forward;
        // the number of levels
        private int level;

        /**
         * Initializes the fields with the required KVPair and the number of
         * levels from the random level method in the SkipList.
         * 
         * @param tempPair
         *            the KVPair to be inserted
         * @param level
         *            the number of levels that the SkipNode should have
         */
        @SuppressWarnings("unchecked")
        public SkipNode(KVPair<K, V> tempPair, int level) {
            pair = tempPair;
            forward = (SkipNode[])Array.newInstance(SkipList.SkipNode.class,
                level + 1);
            this.level = level;
        }


        /**
         * Returns the KVPair stored in the SkipList.
         * 
         * @return the KVPair
         */
        public KVPair<K, V> element() {
            return pair;
        }

    }


    private class SkipListIterator implements Iterator<KVPair<K, V>> {
        private SkipNode current;

        public SkipListIterator() {
            current = head;
        }


        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return current.forward[0] != null;
        }


        @Override
        public KVPair<K, V> next() {
            // TODO Auto-generated method stub
            if (!hasNext()) {
               // throw new NoSuchElementException();  test test 
            	return null;
            }
            current = current.forward[0];
            return current.pair;        
            }

    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        // TODO Auto-generated method stub
        return new SkipListIterator();
    }

}
