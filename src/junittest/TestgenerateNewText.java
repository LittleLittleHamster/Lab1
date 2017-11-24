/**
 * 
 */
package junittest;

import static org.junit.Assert.*;

import org.junit.Test;

import textprocessnew.AdjGraph;

public class TestgenerateNewText {

    @Test
    public void test1() {
        String[] wordList = {"to", "explore", "strange", "new", "worlds", "to", "seek", "out", "new", "life", "and", "new", 
                            "civilizations", "to", "seek", "new", "life", "to", "find", "out"};
        AdjGraph gra = new AdjGraph(wordList);
        String result = gra.generateNewText("to");
        assertEquals("to", result);
    }
    
    @Test
    public void test2() {
        String[] wordList = {"to", "explore", "strange", "new", "worlds", "to", "seek", "out", "new", "life", "and", "new", 
                "civilizations", "to", "seek", "new", "life", "to", "find", "out"};
        AdjGraph gra = new AdjGraph(wordList);
        String result = gra.generateNewText("to life");
        assertEquals("to life", result);
    }
    
    @Test
    public void test3() {
        String[] wordList = {"to", "explore", "strange", "new", "worlds", "to", "seek", "out", "new", "life", "and", "new", 
                "civilizations", "to", "seek", "new", "life", "to", "find", "out"};
        AdjGraph gra = new AdjGraph(wordList);
        String result = gra.generateNewText("to strange");
        assertEquals("to explore strange", result);
    }
    
    @Test
    public void test4() {
        String[] wordList = {"to", "explore", "strange", "new", "worlds", "to", "seek", "out", "new", "life", "and", "new", 
                "civilizations", "to", "seek", "new", "life", "to", "find", "out"};
        AdjGraph gra = new AdjGraph(wordList);
        String result = gra.generateNewText("to out");
        assertEquals("to seek out", result);
    }

}
