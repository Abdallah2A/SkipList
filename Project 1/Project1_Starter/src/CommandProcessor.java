import java.awt.Rectangle;

/**
 * The purpose of this class is to parse a text file into its appropriate, line
 * by line commands for the format specified in the project spec.
 * 
 * @author {Abdallah Ali Hassan}
 * 
 * @version 2021-08-23
 */
public class CommandProcessor {

    // the database object to manipulate the
    // commands that the command processor
    // feeds to it
    private Database data;

    /**
     * The constructor for the command processor requires a database instance to
     * exist, so the only constructor takes a database class object to feed
     * commands to.
     * 
     * @param dataIn
     *            the database object to manipulate
     */
    public CommandProcessor() {
        data = new Database();
    }


    /**
     * This method identifies keywords in the line and calls methods in the
     * database as required. Each line command will be specified by one of the
     * keywords to perform the actions within the database required. These
     * actions are performed on specified objects and include insert, remove,
     * regionsearch, search, intersections, and dump. If the command in the file line is not
     * one of these, an appropriate message will be written in the console. This
     * processor method is called for each line in the file. Note that the
     * methods called will themselves write to the console, this method does
     * not, only calling methods that do.
     * 
     * @param line
     *            a single line from the text file
     */
    public void processor(String line) {
        // Split the command into individual words
        String[] words = line.split("\\s+");
        
        // Perform an action based on the first word of the command
        switch (words[0]) {
        
        	// Insert a new rectangle into the data structure
            case "insert":
                String name = words[1];
                // Casting string to int
                int x = Integer.parseInt(words[2]);
                int y = Integer.parseInt(words[3]);
                int w = Integer.parseInt(words[4]);
                int h = Integer.parseInt(words[5]);
                // Creating rectangle with the given value
                Rectangle rect = new Rectangle(x, y, w, h);
                // Creating pair with the name and rectangle
                KVPair<String, Rectangle> newPair = new KVPair<String, Rectangle>(name,rect);
                data.insert(newPair);
                break;
                
            // Remove a rectangle from the data structure
            case "remove":
                // Remove by name (1 for 'remove' and other for 'name')
                if (words.length == 2) {
                    data.remove(words[1]);
                }
                // Remove by value (1 for remove and others for x ,y ,width and height)
                else if (words.length == 5) {
                    data.remove(Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]), Integer.parseInt(words[4]));
                }
                break;
                
            // Search for rectangles with the given name
            case "search":
                data.search(words[1]);
                break;
                
            // Search for rectangles that intersect with the given region
            case "regionsearch":
            	// Casting string to int
                int xx = Integer.parseInt(words[1]);
                int yy = Integer.parseInt(words[2]);
                int ww = Integer.parseInt(words[3]);
                int hh = Integer.parseInt(words[4]);
                data.regionsearch(xx, yy, ww, hh);
                break;
                
            // Print all pairs of rectangles that intersect
            case "intersections":
                data.intersections();
                break;
                
            // Dump the data structure
            case "dump":
                data.dump();
                break;
                
            // Handle invalid commands
            default:
                System.out.println("Invalid command: " + line);
        }
    }

}
