import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is responsible for interfacing between the command processor and
 * the SkipList. The responsibility of this class is to further interpret
 * variations of commands and do some error checking of those commands. This
 * class further interpreting the command means that the two types of remove
 * will be overloaded methods for if we are removing by name or by coordinates.
 * Many of these methods will simply call the appropriate version of the
 * SkipList method after some preparation.
 * 
 * @author {Abdallah Ali Hassan}
 * 
 * @version 2021-08-23
 */
public class Database {

    // this is the SkipList object that we are using
    // a string for the name of the rectangle and then
    // a rectangle object, these are stored in a KVPair,
    // see the KVPair class for more information
	private SkipList<String, Rectangle> list;

    /**
     * The constructor for this class initializes a SkipList object with String
     * and Rectangle a its parameters.
     */
    public Database() {
        list = new SkipList<String, Rectangle>();
    }


    /**
     * Inserts the KVPair in the SkipList if the rectangle has valid coordinates
     * and dimensions, that is that the coordinates are non-negative and that
     * the rectangle object has some area (not 0, 0, 0, 0). This insert will
     * insert the KVPair specified into the sorted SkipList appropriately
     * 
     * @param pair
     *            the KVPair to be inserted
     */
    public void insert(KVPair<String, Rectangle> pair) {
        // Check if the rectangle satisfies the required conditions
    	if (pair.getValue().width > 0 && pair.getValue().height > 0
                && pair.getValue().x >= 0 && pair.getValue().y >= 0
                && pair.getValue().x + pair.getValue().width <= 1024
                && pair.getValue().y + pair.getValue().height <= 1024
                && pair.getKey().matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
            // Insert the pair into the skip list
	        list.insert(pair);
	        System.out.printf("Rectangle inserted: (%s, %d, %d, %d, %d)\n",
	        		pair.getKey(), pair.getValue().x, pair.getValue().y, pair.getValue().width, pair.getValue().height);
	    }
    	
    	else {
            // Print a message indicating that the rectangle was rejected
	        System.out.printf("Rectangle rejected: (%s, %d, %d, %d, %d)\n",
	        		pair.getKey(), pair.getValue().x, pair.getValue().y, pair.getValue().width, pair.getValue().height);
    	}
    }

    
    /**
     * Removes a rectangle with the name "name" if available. If not an error
     * message is printed to the console.
     * 
     * @param name
     *            the name of the rectangle to be removed
     */
    public void remove(String name) {
        // Remove the rectangle with the specified name and get the removed KVPair
    	KVPair<String, Rectangle> removedRect =  list.remove(name);
    	
        // If a rectangle was removed, print a message with its details
    	if(removedRect != null) {
	        System.out.printf("Rectangle removed: (%s, %d, %d, %d, %d)\n",
	        		removedRect.getKey(), removedRect.getValue().x, removedRect.getValue().y, removedRect.getValue().width, removedRect.getValue().height);
    	}
    	
        // If no rectangle was removed, print a message indicating it was not found
    	else {
	        System.out.printf("Rectangle not found: (%s)\n", name);
    	}
    }


    /**
     * Removes a rectangle with the specified coordinates if available. If not
     * an error message is printed to the console.
     * 
     * @param x
     *            x-coordinate of the rectangle to be removed
     * @param y
     *            x-coordinate of the rectangle to be removed
     * @param w
     *            width of the rectangle to be removed
     * @param h
     *            height of the rectangle to be removed
     */
    public void remove(int x, int y, int w, int h) {
        // Create a new rectangle object based on the input coordinates
		Rectangle rect = new Rectangle(x, y, w, h);
	    // Remove the rectangle that corresponds to the input rectangle and get the removed KVPair
    	KVPair<String, Rectangle> removedRect =  list.removeByValue(rect);
    	
        // Check if the rectangle was successfully removed
    	if(removedRect != null) {
	        System.out.printf("Rectangle removed: (%s, %d, %d, %d, %d)\n",
	        		removedRect.getKey(), removedRect.getValue().x, removedRect.getValue().y, removedRect.getValue().width, removedRect.getValue().height);
    	}
    	
    	else {
            // If the rectangle was not found in the list, print an error message
	        System.out.printf("Rectangle not found: (%d %d %d %d)\n", x, y, w, h);
    	}
    }


    /**
     * Displays all the rectangles inside the specified region. The rectangle
     * must have some area inside the area that is created by the region,
     * meaning, Rectangles that only touch a side or corner of the region
     * specified will not be said to be in the region. You will need a SkipList Iterator for this 
     * 
     * @param x
     *            x-Coordinate of the region
     * @param y
     *            y-Coordinate of the region
     * @param w
     *            width of the region
     * @param h
     *            height of the region
     */
    public void regionsearch(int x, int y, int w, int h) {
        // Check for valid input values
        if (w <= 0 || h <= 0) {
        	System.out.printf("Rectangle rejected (%d, %d, %d, %d):\n", x, y, w, h);
            return;
        }
        
        // Display the search region head
        System.out.printf("Rectangles intersecting region (%d, %d, %d, %d):\n", x, y, w, h);
        
        // Create a rectangle object to represent the search region
        Rectangle searchRegion = new Rectangle(x, y, w, h);
        
        // Iterate over each element in the list to check if it intersects with the search region
        Iterator<KVPair<String, Rectangle>> iterator = list.iterator();
        
        while (iterator.hasNext()) {
            KVPair<String, Rectangle> currentRect = iterator.next();
            Rectangle rectRegion = currentRect.getValue();
            
            // Check if the current rectangle intersects with the search region
            if (searchRegion.intersects(rectRegion)) {
                // Display the current rectangle if it intersects with the search region
            	System.out.printf("(%s, %d, %d, %d, %d)\n",
            			currentRect.getKey(), rectRegion.x, rectRegion.y, rectRegion.width, rectRegion.height);
            }
        }
    }


    /**
     * Prints out all the rectangles that Intersect each other by calling the
     * SkipList method for intersections. You will need to use two SkipList Iterators for this
     */
    public void intersections() {
    	// print a header for the Intersection pairs
        System.out.println("Intersection pairs: ");
        // iterate through the list of rectangles
        Iterator<KVPair<String, Rectangle>> iter1 = list.iterator();
        // loop over each rectangle in the list
        while (iter1.hasNext()) {
        	// get the current rectangle and its value
        	KVPair<String, Rectangle> pair1 = iter1.next();
            Rectangle rect1 = pair1.getValue();
            
            // create a second iterator to loop over the list again
            Iterator<KVPair<String, Rectangle>> iter2 = list.iterator();
            
            // loop over each rectangle in the list again
            while (iter2.hasNext()) {
            	// get the current rectangle and its value
                KVPair<String, Rectangle> pair2 = iter2.next();
                Rectangle rect2 = pair2.getValue();
                
                // check if the two rectangles are different and intersect
                if (pair2 != pair1 && rect1.intersects(rect2)) {
                    // print the names and coordinates of the intersecting rectangles
                    System.out.printf("(%s, %d, %d, %d, %d | %s, %d, %d, %d, %d)\n",
                    		pair1.getKey(),rect1.x,rect1.y,rect1.width,rect1.height,
                    		pair2.getKey(),rect2.x,rect2.y,rect2.width,rect2.height);
                }
            }
        }
    }


    /**
     * Prints out all the rectangles with the specified name in the SkipList.
     * This method will delegate the searching to the SkipList class completely.
     * 
     * @param name
     *            name of the Rectangle to be searched for
     */
    public void search(String name) {
    	// Search for all rectangles with the given name
    	ArrayList<KVPair<String,Rectangle>> foundRects = list.search(name);
    	
    	// If any rectangles are found, print their information
    	if(!foundRects.isEmpty()) {
	    	for(KVPair<String,Rectangle> rect : foundRects) {
	    		System.out.printf("(%s, %d, %d, %d ,%d)\n",
	    				rect.getKey(),rect.getValue().x,rect.getValue().y,rect.getValue().width,rect.getValue().height);
	    	}
    	}
    	
    	// If no rectangles are found, print a message indicating that
    	else {
			System.out.printf("Rectangle not found: %s \n" ,name);
		}
	}


    /**
     * Prints out a dump of the SkipList which includes information about the
     * size of the SkipList and shows all of the contents of the SkipList. This
     * will all be delegated to the SkipList.
     */
    public void dump() {
    	// It just calling dump method in the skipList class
        list.dump();
    }

}
