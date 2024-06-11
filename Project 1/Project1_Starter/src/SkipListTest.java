import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class SkipListTest {
    SkipList skipListObject;
    @BeforeEach
   public  void setUp(){
        skipListObject = new SkipList();
    }
    @RepeatedTest(value = 21)
    //randomLevel function should return an integer or 0
    void testRandomLevel() {
        assertTrue(skipListObject.randomLevel()>=0);
    }

    @Test
    //size function should return the number on inserted object in the skip list
    void testSize(){
        assertEquals(0, skipListObject.size());
        Rectangle rectangleForTest = new Rectangle(1,3,3,3);
        KVPair it=new KVPair("a",rectangleForTest);
        skipListObject.insert(it);
        assertEquals(1,skipListObject.size());
        skipListObject.insert(it);
        assertEquals(2,skipListObject.size());
        skipListObject.insert(it);
        assertEquals(3,skipListObject.size());
        skipListObject.remove(it.getKey());
        assertEquals(2,skipListObject.size());
        skipListObject.removeByValue(it.getValue());
        assertEquals(1,skipListObject.size());
    }

    @Test
    //insert function should insert just one object without any changes in its values
    void testIinsert(){
        Rectangle rectangleForTest = new Rectangle(1,3,3,3);
        KVPair it=new KVPair("a",rectangleForTest);
        assertEquals(0, skipListObject.size());
        skipListObject.insert(it);
        assertEquals(1, skipListObject.size());
        assertTrue(skipListObject.search("a").get(0).equals(it));

    }


    @Test
    //search function should return an array list that contain all pairs with specific key
    // and return an empty one if there is no pairs with that key
    void testSearchByKey(){
        Rectangle rectangleForTest = new Rectangle(1,2,3,5);
        Rectangle rectangleForTest1 = new Rectangle(1,3,7,4);
        Rectangle rectangleForTest2 = new Rectangle(1,1,3,3);
        KVPair it=new KVPair("a",rectangleForTest);
        KVPair it1=new KVPair("b",rectangleForTest1);
        KVPair it2=new KVPair("c",rectangleForTest2);
        skipListObject.insert(it);
        skipListObject.insert(it1);
        skipListObject.insert(it2);

        assertTrue(skipListObject.search("a").get(0).equals(it)&&skipListObject.search("a").size()==1);

        assertEquals(0, skipListObject.search("v").size());

    }

    @Test
    //remove function should remove pair with specific key and return it
    //it should do nothing and return null if there is no pair with that key
    public void testRemove() {
        skipListObject.insert(new KVPair<>("a", new   Rectangle(1,2,3,5)));
        skipListObject.insert(new KVPair<>("b",new Rectangle(1,2,5,5)));
        skipListObject.insert(new KVPair<>("c", new Rectangle(1,4,3,5)));
        skipListObject.insert(new KVPair<>("d", new Rectangle(1,2,3,2)));
        skipListObject.insert(new KVPair<>("e", new Rectangle(2,2,3,5)));

        KVPair<String, Rectangle> removedPair = skipListObject.remove("a");
        assertEquals(new   Rectangle(1,2,3,5), removedPair.getValue());
        assertEquals(4, skipListObject.size());

        assertNull(skipListObject.remove("z"));
        assertEquals(4, skipListObject.size());
    }
    @Test
    //remove function should remove pair with specific value and return it
    //it should do nothing and return null if there is no pair with that value
    public void testRemoveByValue() {
        skipListObject.insert(new KVPair<>("a", new Rectangle(6, 6,2,2)));
        skipListObject.insert(new KVPair<>("b", new Rectangle(7, 7,4,4)));
        KVPair<String, Rectangle> removed = skipListObject.removeByValue(new Rectangle(6, 6,2,2));
        assertEquals(1, skipListObject.size());
        assertEquals("a", removed.getKey());
        assertEquals( new Rectangle(6, 6, 2, 2), removed.getValue());


        ArrayList<KVPair<String, Rectangle>> searchResult = skipListObject.search("a");
        assertTrue(searchResult.isEmpty());

        removed = skipListObject.removeByValue(new Rectangle(10, 10,1,1));
        assertNull(removed);
        assertEquals(1, skipListObject.size());
    }

}