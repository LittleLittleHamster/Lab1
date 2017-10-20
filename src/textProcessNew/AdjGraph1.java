package textprocessnew;

import gviz.GraphViz;
import java.io.File;

import java.util.ArrayList;
import java.util.Random;

/**.
* 
*
* @author George Bush
*/
public class AdjGraph1 extends AdjGraph  {
  /**.
  * @author George Bush
  */
  
  public AdjGraph1(final String ... wordList) {
      //do somethings
      super(wordList);
  }
  /**.
   *桥接词
  */
  
  public final String queryBridgeWords(final String word1, final String word2) {     
    String res;   
    if (!collectList.contains(word1) && collectList.contains(word2)) {
      res = "No \"" + word1 + "\" in the Graph!";
    } else if (collectList.contains(word1) && !collectList.contains(word2)) {
      res = "No \"" + word2 + "\" in the Graph!";
    } else if (collectList.contains(word1) && collectList.contains(word2)) {
      int indexWord1;
      indexWord1 = collectList.indexOf(word1);
      final ArrayList bridgeWord = new ArrayList();
      final VertexNode node = vertexList[indexWord1];
      final ArrayList edgelis1 = new ArrayList(node.edgeList);      
      for (int i = 0; i < edgelis1.size(); i++) {
        final EdgeNode veTemp = (EdgeNode) edgelis1.get(i);
        if (hasEdge(veTemp.endWord, word2)) {
          bridgeWord.add(veTemp.endWord);
        }
      }
      if (bridgeWord.isEmpty()) {
        res = "No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!";
      } else {
        bridgeWord.trimToSize();
        String[] bridgeWordArray;
        bridgeWordArray = new String[bridgeWord.size()];
        for (int i = 0; i < bridgeWord.size(); i++) {
          bridgeWordArray[i] = (String) bridgeWord.get(i);
        }
        int len;
        len = 1;
        if (bridgeWordArray.length == len) {
          res = "The bridge words from \"" + word1 + "\" to \"" + word2 + "\" is: "
            + bridgeWordArray[0] + ".";
          final GraphViz gviz = new GraphViz();
          gviz.addln(gviz.start_graph());
          gviz.addln(setsize);
          gviz.addln(setedgecolor);
          //填充dot语言代码
          for (int i = 0; i < this.numofvertex; i++) {
            final VertexNode node1 = vertexList[i];
            final ArrayList edgelis = new ArrayList(node1.edgeList);
            for (int j = 0; j < this.vertexList[i].edgeList.size(); j++) {
              final EdgeNode veTemp = (EdgeNode) edgelis.get(j);
              gviz.addln(veTemp.startWord + setturn + veTemp.endWord
                  + setstyle + veTemp.weight + setend);
            }
          }
          final String addstr = bridgeWordArray[0] + "[shape=box];";
          gviz.addln(addstr);
          gviz.addln(gviz.end_graph());
          gviz.increaseDpi();
          final File out1 = new File("../Lab1/src/BridgeWord." + type);    // Windows
          gviz.writeGraphToFile(gviz.getGraph(gviz.getDotSource(), type, repesentationType), out1);
        } else {
          res = "The bridge words from \"" + word1 + "\" to \"" + word2 + "\" are: ";
          final GraphViz gviz = new GraphViz();
          gviz.addln(gviz.start_graph());
          gviz.addln(setsize);
          gviz.addln(setedgecolor);
          //填充dot语言代码
          for (int i = 0; i < this.numofvertex; i++) {
            final VertexNode node1 = vertexList[i];
            final ArrayList edgelis = new ArrayList(node1.edgeList);
            for (int j = 0; j < this.vertexList[i].edgeList.size(); j++) {
              final EdgeNode veTemp = (EdgeNode) edgelis.get(j);
              gviz.addln(veTemp.startWord + setturn + veTemp.endWord
                  + setstyle + veTemp.weight + setend);
            }
          }
          for (int i = 0; i < bridgeWordArray.length - 2; i++) {
            final String ress = res + bridgeWordArray[i] + ", ";
            res = ress;
            final String addstr = bridgeWordArray[i] + " [shape=box];";
            gviz.addln(addstr);
          }
          final String ress = res + bridgeWordArray[bridgeWordArray.length - 2] + " and " 
              + bridgeWordArray[bridgeWordArray.length - 1] + ".";
          res = ress;
          for (int i = 0; i < bridgeWordArray.length; i++) {
            final String addstr = bridgeWordArray[i] + " [shape=box];";
            gviz.addln(addstr);
          }
          gviz.addln(gviz.end_graph());
          gviz.increaseDpi();
          final File out1 = new File("../Lab1/src/BridgeWord." + type);    // Windows
          gviz.writeGraphToFile(gviz.getGraph(gviz.getDotSource(), type, repesentationType), out1);
        }            
      }
    } else {
      res = "No \"" + word1 + "\" and \"" + word2 + "\" in the Graph!";
    }
    return res;
  }

  /**.
   *生成新文本
  */
  public String generateNewText(final String inputText) {
    final String willProcTxt = inputText;
    final String[] inputTxtArray = willProcTxt.split(zhengze);
    //final String[] inputTxtArray = inputText.split(zhengze);
    String res = inputTxtArray[0];
      
    Random random ;
    random = new Random();
    for (int i = 0; i < inputTxtArray.length - 1; i++) {
      //final String searchres = queryBridgeWords(inputTxtArray[i]
      //.toLowerCase(Locale.US).replaceAll("[^a-z]", " ").trim(), 
      // inputTxtArray[i + 1].toLowerCase(Locale.US).replaceAll("[^a-z]", " ").trim());
      final String searchres = new String(queryBridgeWords(wordtolow(i,inputTxtArray),
            wordtolow(i + 1,inputTxtArray)));
      if (searchres.startsWith("No")) {
        final String ress = res + " " + inputTxtArray[i + 1];
        res = ress;
      //res.append(" ").append(inputTxtArray[i + 1]);
      } else {
        //final String[] brgWord = searchres.split(":")[1].replaceAll("and", " ")
        //.replaceAll("[^a-z]", " ").trim().split(zhengze);
        final String[] brgWord = changeword(searchres);
        int len;
        len = 1;
        if (brgWord.length == len) {
          final String ress = res + " " + brgWord[0] + " " + inputTxtArray[i + 1];
          res = ress;
        } else {
          final int randres = random.nextInt(brgWord.length);
          final String ress = res + " " + brgWord[randres] + " " + inputTxtArray[i + 1];
          res = ress;
        }
      }
    }
    return res;
  }
  /**.
   *最短路径
  */
  
  public String calcShortestPath(final String word1,final String word2) {
    final String strPath = showShortestPath(word1, word2, false);
    String str;
    if (unable.equals(strPath)) {
      final int index1 = collectList.indexOf(word1);
      final int index2 = collectList.indexOf(word2);
      final String res = "路径为：" + strPath + "；路径长度为：" 
          + dist[index1][index2];

      final GraphViz gviz = new GraphViz();
      gviz.addln(gviz.start_graph());
      gviz.addln(setsize);
      gviz.addln(setedgecolor);
      //填充dot语言代码
      //gviz.addln("A -> B;");
      for (int i = 0; i < this.numofvertex; i++) {
        final VertexNode node = vertexList[i];
        final ArrayList edgelis = new ArrayList(node.edgeList);
        for (int j = 0; j < this.vertexList[i].edgeList.size(); j++) {
          final EdgeNode veTemp = (EdgeNode) edgelis.get(j);
          gviz.addln(veTemp.startWord + setturn + veTemp.endWord
              + setstyle + veTemp.weight + setend);
        }
      }

      gviz.addln("edge [color=red]");
      gviz.addln(strPath);

      gviz.addln(gviz.end_graph());
      gviz.increaseDpi();
      //System.out.println(gviz.getDotSource());
      log.fine(gviz.getDotSource());
      final File out1 = new File("../Lab1/src/OneShortestPath." + type);    // Windows
      gviz.writeGraphToFile(gviz.getGraph(gviz.getDotSource(), type, repesentationType), out1);

      str = res;
    } else {
      str = unable;
    }
    return str;
  }



  /**.
   *
   */
  public String getEndNodeOptions(final String word1) {
    String res = "";
    for (int i = 0; i < collectList.size(); i++) {
      final String strr = new String((String) collectList.get(i));
      if (!strr.equals(word1)) {
        final String ress = res + collectList.get(i) + " ";
        res = ress;
      }
    }
    return res;
  }

  /**.
   *展示单元最短路径
  */
  public String[] showonesourcepath(final String word1) {
    final String optionstr = new String(getEndNodeOptions(word1));
    final String[] options = optionstr.split(zhengze);
    String[] res = new String[options.length];
    for (int i = 0; i < options.length; i++) {
      final String optionsi = options[i];
      final String str = new String(showShortestPath(word1, optionsi, false));
      final boolean bool = str.equals(unable);
      if (bool) {
        res[i] = showShortestPath(word1, optionsi, false);
      } else {
        final int index1 = collectList.indexOf(word1);
        final int index2 = collectList.indexOf(optionsi);
        final int distnum = dist[index1][index2];
        res[i] = showShortestPath(word1, optionsi, false) + ";路径长度为：" 
          + distnum;
      }
    }

    return res; 
  }

  /**.
   *展示多源最短路径
  */
  public boolean showTowNodeAllShortPath(final String word1, final String word2) {
    final GraphViz gviz = new GraphViz();
    gviz.addln(gviz.start_graph());
    gviz.addln(setsize);
    gviz.addln(setedgecolor);
    //填充dot语言代码
    //gviz.addln("A -> B;");this is a alternation
    for (int i = 0; i < this.numofvertex; i++) {
      final VertexNode node = vertexList[i];
      final ArrayList edgelis = new ArrayList(node.edgeList);
      for (int j = 0; j < this.vertexList[i].edgeList.size(); j++) {
        final EdgeNode veTemp = (EdgeNode) edgelis.get(j);
        gviz.addln(veTemp.startWord + setturn + veTemp.endWord
            + setstyle + veTemp.weight + setend);
      }
    }

    gviz.addln("edge [color=green]");

    final boolean canArrive = showAllPath(gviz, word1, word2);

    gviz.addln(gviz.end_graph());

    gviz.increaseDpi();
    final File out1 = new File("../Lab1/src/TwoNodeAllShortestPath." + type);    // Windows
    gviz.writeGraphToFile(gviz.getGraph(gviz.getDotSource(), type, repesentationType), out1);

    return canArrive;
  }

  /**.
   *
  */
  public boolean hasVertex(final String word) {
    return collectList.contains(word);
  }

}
