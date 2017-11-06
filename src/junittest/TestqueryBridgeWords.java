/**
 * 
 */
package junittest;

import static org.junit.Assert.*;

import org.junit.Test;

import textprocessnew.AdjGraph;


/**
 * @author admin
 *
 */
public class TestqueryBridgeWords {

    /**
     * Test method for {@link textprocessnew.AdjGraph#queryBridgeWords(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testQueryBridgeWords1() {
        String[] wordList = {"to", "explore", "strange", "new", "worlds", "to", "seek", "out", "new", "life", "and", "new", "civilizations"};
        AdjGraph gra = new AdjGraph(wordList);
        String result = gra.queryBridgeWords("to", "out");
        assertEquals("The bridge words from \"to\" to \"out\" is: seek.", result);
    }
    
    @Test
    public void testQueryBridgeWords2() {
        String[] wordList = {"to", "explore", "strange", "new", "worlds", "to", "seek", "out", "new", "life", "and", "new", "civilizations"};
        AdjGraph gra = new AdjGraph(wordList);
        String result = gra.queryBridgeWords("��", "��");
        assertEquals("��������ĸ�ַ������������룡", result);
    }
    
    @Test
    public void testQueryBridgeWords3() {
        String[] wordList = {"to", "explore", "strange", "new", "worlds", "to", "seek", "out", "new", "life", "and", "new", "civilizations"};
        AdjGraph gra = new AdjGraph(wordList);
        String result = gra.queryBridgeWords("too", "out");
        assertEquals("No \"too\" in the Graph!", result);
    }
    
    @Test
    public void testQueryBridgeWords4() {
        String[] wordList = {"to", "explore", "strange", "new", "worlds", "to", "seek", "out", "new", "life", "and", "new", "civilizations"};
        AdjGraph gra = new AdjGraph(wordList);
        String result = gra.queryBridgeWords("", "out");
        assertEquals("���ڿյ��ʣ����������룡", result);
    }

}
