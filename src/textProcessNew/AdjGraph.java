package textprocessnew;

import gviz.GraphViz;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Logger; 


/**.
* @author Gorge Bush
*/

public class AdjGraph {
  /**.
  * @author Gorge Bush
  */
  public static final Logger log = Logger.getLogger(AdjGraph.class.toString());  
  /**.
  * @author George Bush
  */
  protected transient int numofvertex;
  /**.
  * @author George Bush
  */
  protected transient VertexNode[] vertexList;
  /**.
  * @author George Bush
  */
  protected transient int[][] vertexMatrix;
  /**.
  * @author George Bush
  */
  protected transient List collectList = new ArrayList();
  /**.
  * @author George Bush
  */
  protected transient String pathTemp;
  /**.
  * @author George Bush
  */
  protected transient int[][] dist;
  /**.
  * @author George Bush
  */
  protected transient int[][][] midList;
  /**.
  * @author George Bush
  */
  protected static String type = "png";
  /**.
  * @author George Bush
  */  
  protected static String repesentationType = "dot";
  /**.
  * @author George Bush
  */  
  protected static String unable = "不可达";  
  /**.
  * @author George Bush
  */  
  protected static String zhengze = "\\s+";
  /**.
  * @author George Bush
  */  
  protected static String setedgecolor = "edge [color=black]"; 
  /**.
  * @author George Bush
  */  
  protected static String setstyle = " [style=bold,label=\"";
  /**.
  * @author George Bush
  */  
  protected static String setend = "\"];";  
  /**.
  * @author George Bush
  */  
  protected static String setsize = "size = \"15,4.3\"";
  /**.
  * @author George Bush
  */  
  protected static String setturn = " -> ";
  
  /**.
   *构造函数
   */
  public AdjGraph(final String ... wordList) {
    //字符串数组去重
    for (int i = 0; i < wordList.length; i++) {
      if (!collectList.contains(wordList[i])) {
        collectList.add(wordList[i]);
      }
    }

    numofvertex = collectList.size();

    vertexList = new VertexNode[numofvertex];

    //构造顶点表
    
    for (int i = 0; i < numofvertex; i++) {
      vertexList[i] = new VertexNode();
      vertexList[i].vertex = (String) collectList.get(i);
      vertexList[i].edgeList = new ArrayList();
    }

    //构造边表
    for (int i = 0; i < wordList.length - 1; i++) {
      final EdgeNode tempEdgeNode = new EdgeNode();
      final int indexVertexNode = collectList.indexOf(wordList[i]);
      if (vertexList[indexVertexNode].edgeList.size() == 0) {
        tempEdgeNode.startVer = indexVertexNode;
        tempEdgeNode.endVer = collectList.indexOf(wordList[i + 1]);
        tempEdgeNode.startWord = wordList[i];
        tempEdgeNode.endWord = wordList[i + 1];
        tempEdgeNode.weight = 1;
        vertexList[indexVertexNode].edgeList.add(tempEdgeNode);}
      else {
        byte eflag = 0;
        for (int j = 0; j < vertexList[indexVertexNode].edgeList.size(); j++) {
          final VertexNode node = vertexList[indexVertexNode];
          final ArrayList edgelistnode = new ArrayList(node.edgeList);
          final EdgeNode veTemp = (EdgeNode) edgelistnode.get(j);
          if (veTemp.endWord.equals(wordList[i + 1])) {
            eflag = 1;
            final EdgeNode newedgenode = new EdgeNode();
            newedgenode.startVer = veTemp.startVer;
            newedgenode.startWord = veTemp.startWord;
            newedgenode.endVer = veTemp.endVer;
            newedgenode.endWord = veTemp.endWord;
            newedgenode.weight = veTemp.weight + 1;
            vertexList[indexVertexNode].edgeList.set(j, newedgenode);
            break;
          }
        }
        if (eflag == 0) {
          final EdgeNode newedgenode = new EdgeNode();
          newedgenode.startVer = indexVertexNode;
          newedgenode.startWord = wordList[i];
          newedgenode.endVer = collectList.indexOf(wordList[i + 1]);
          newedgenode.endWord = wordList[i + 1];
          newedgenode.weight = 1;
          vertexList[indexVertexNode].edgeList.add(newedgenode);
        }
      }
    }

    for (int i = 0; i < numofvertex; i++) {
      ((ArrayList) vertexList[i].edgeList).trimToSize();
    }

    adjlisttoadjmatrix();
    modFloyd();
  }

  /**.
   *展示图
   */
  public void showDirectedGraph() {
    final File files = new File("../Lab1/src/DirectedGraph.png");
    files.delete();
    final GraphViz gviz = new GraphViz();
    gviz.addln(gviz.start_graph());
    gviz.addln(setsize);
    //填充dot语言代码
    //gviz.addln("A -> B;");
    VertexNode node = new VertexNode();
    for (int i = 0; i < this.numofvertex; i++) {
      node = vertexList[i];
      final ArrayList edgelis = new ArrayList(node.edgeList);
      for (int j = 0; j < this.vertexList[i].edgeList.size(); j++) {
        final EdgeNode veTemp = (EdgeNode) edgelis.get(j);
        gviz.addln(veTemp.startWord + setturn + veTemp.endWord
            + setstyle + veTemp.weight + setend);
      }
    }

    gviz.addln(gviz.end_graph());
    gviz.increaseDpi();
    final File out1 = new File("../Lab1/src/DirectedGraph." + type);    // Windows
    gviz.writeGraphToFile(gviz.getGraph(gviz.getDotSource(), type, repesentationType), out1);
  }



  /**.
   *字符串处理
   */
  protected String[] changeword(final String searchres) {
    final String[] str = searchres.split(":");
    final String str1 = new String(str[1]);
    final String str2 = new String(str1.replaceAll("and", " "));
    final String str3 = new String(str2.replaceAll("[^a-z]", " "));
    final String str4 = str3.trim();
    return str4.split(zhengze);
  }
  /**.
   *字符串处理
   */

  protected String wordtolow(final int index,final String ... inputTxtArray) {
    final String str = new String(inputTxtArray[index]);
    final String str1 = new String(str.toLowerCase(Locale.US));
    final String str2 = new String(str1.replaceAll("[^a-z]", " "));
    //str3 = str2.trim();
    return str2.trim();
  }
  
  //内部方法
  /**.
   * 两个顶点之间是否有边
   */  

  protected boolean hasEdge(final String word1, final String word2) {
    int indexWord1;
    boolean flag = false;
    indexWord1 = collectList.indexOf(word1);
    final VertexNode node = vertexList[indexWord1];
    final ArrayList edgelis1 = new ArrayList(node.edgeList);
    for (int i = 0; i < edgelis1.size(); i++) {
      final EdgeNode veTemp = (EdgeNode) edgelis1.get(i);
      final String endword = new String(veTemp.endWord);
      if (endword.equals(word2)) {
        flag = true;
      }
    }
    return flag;
  }

  //将邻接表转换为邻接矩阵
  private void adjlisttoadjmatrix() {
    vertexMatrix = new int[numofvertex][numofvertex];
    //初始化邻接矩阵
    for (int i = 0; i < numofvertex; i++) {
      for (int j = 0; j < numofvertex; j++) {
        vertexMatrix[i][j] = 10000;
      }
    }
    VertexNode node = new VertexNode();
    for (int i = 0; i < numofvertex; i++) {
      node = vertexList[i];
      final ArrayList edgelis = new ArrayList(node.edgeList);
      for (int j = 0; j < edgelis.size(); j++) {
        final EdgeNode veTemp = (EdgeNode) edgelis.get(j);
        vertexMatrix[veTemp.startVer][veTemp.endVer] = veTemp.weight;
      }
    }
  }

  //使用改进的Floyd算法计算最短路径
  private void modFloyd() {
    //首先进行初始化操作
    dist = new int[numofvertex][numofvertex];
    for (int i = 0; i < numofvertex; i++) { 
      for (int j = 0; j < numofvertex; j++) {
        dist[i][j] = vertexMatrix[i][j];
      }
    }

    midList = new int[numofvertex][numofvertex][numofvertex];
    for (int i = 0; i < numofvertex; i++) {
      for (int j = 0; j < numofvertex; j++) {
        for (int k = 0; k < numofvertex; k++) {
          midList[i][j][k] = -1;
        }
      }
    }

    //执行改进的Floyd算法
    for (int k = 0; k < numofvertex; k++) {
      for (int i = 0; i < numofvertex; i++) {
        for (int j = 0; j < numofvertex;j++) {
          if (dist[i][j] > dist[i][k] + dist[k][j]) {
            dist[i][j] = dist[i][k] + dist[k][j];
            for (int l = 0; l < numofvertex; l++) {
              midList[i][j][l] = -1;
            }
            midList[i][j][0] = k;
          } else if (dist[i][j] == dist[i][k] + dist[k][j]) {
            for (int l = 0; l < numofvertex; l++) {
              if (midList[i][j][l] == -1) {
                midList[i][j][l] = k;
                break;
              }
            }
          }
        }
      }
    }
  }

  /**.
   *展示最短路径
   */
  
  protected String showShortestPath(final String word1, final String word2, final boolean showAll) {
    String res;
    if (showAll) {
      res = "已显示在图中！";
    } else {
      final int startNode = collectList.indexOf(word1);
      final int endNode = collectList.indexOf(word2);
      
      int num;
      num = 10000;
      if (dist[startNode][endNode] < num) {
        res = word1 + getPath(startNode, endNode) + "->" + word2;
      } else {
        res = unable;
      }
      
    }
    return res;
  }

  //读取路径
  private String getPath(final int startNode, final int endNode) {
    String str;
    final int[] midnum = midList[startNode][endNode];
    final int mid0 = midnum[0];
    /*if (mid0 != -1 && mid0 != startNode && mid0 != endNode) {
      str = getPath(startNode, mid0)
        + "->" + collectList.get((int) mid0) 
        + getPath((int) mid0, endNode);
    } else */
    if (mid0 == endNode) {
      str = "->" + collectList.get(endNode);
    } else if (mid0 != -1 && mid0 == startNode) {
      str = "";
    } else if (mid0 == -1 && mid0 != startNode) {
      str = "";
    } else if (mid0 == -1 && mid0 == startNode) {
      str = "";
    } else { 
      str = getPath(startNode, mid0) + "->" + collectList.get((int) mid0) 
                + getPath((int) mid0, endNode); 
    }
    return str;
  }

  
  /**.
   *显示两点间所有最短路径
   */
  
  protected boolean showAllPath(final GraphViz gviz, final String word1, final String word2) {
    final int startNode = collectList.indexOf(word1);
    final int endNode = collectList.indexOf(word2);
    //pathTemp = new String();
    int num;
    num = 10000;
    boolean flag;
    if (dist[startNode][endNode] < num) {
      getAllPath(startNode, endNode);
      final String[] pathArray = pathTemp.split(";");
      final List collectPath = new ArrayList();
      for (int i = 0; i < pathArray.length; i++) { 
        final String pathstr = pathArray[i];
        if (!collectPath.contains(pathstr)) {
          collectPath.add(pathstr);
        }
      }
      for (int i = 0; i < collectPath.size(); i++) {
        gviz.addln(collectPath.get(i) + ";");
      }
      flag = true;
    } else {
      flag = false;
    }
    return flag;
  }

  private void getAllPath(final int startNode,final int endNode) {
    int count;
    for (count = 0; count < numofvertex; count++) {
      if (midList[startNode][endNode][count] == -1) {
        break;
      }
    }
    if (count == 0) {
      pathTemp = pathTemp + vertexList[startNode].vertex + setturn 
        + vertexList[endNode].vertex + ";";
      return;
    }
    for (int i = 0; i < count; i++) {
      final int midnum = midList[startNode][endNode][i];
      getAllPath(startNode, midnum); 
      getAllPath(midnum, endNode);
    }
  }
 
  /**.
   *随机游走，返回随机游走的字符串
  */
  public String randomWalk() {
    //遍历所有的边，初始化
    for (int i = 0; i < numofvertex; i++) {
      if (vertexList[i].edgeList.size() > 0) {
        for (int j = 0; j < vertexList[i].edgeList.size(); j++) {
          final EdgeNode veTemp = (EdgeNode) vertexList[i].edgeList.get(j);
          final EdgeNode newTemp = new EdgeNode();
          newTemp.startVer = veTemp.startVer;
          newTemp.startWord = veTemp.startWord;
          newTemp.endVer =  veTemp.endVer;
          newTemp.endWord = veTemp.endWord;
          newTemp.weight = veTemp.weight;
          newTemp.visited = false;
          vertexList[i].edgeList.set(j, newTemp);
        }
      }
    }
    Random random ;
    random = new Random();
    int randNextNode = random.nextInt(numofvertex);
    int nowIndex;
    String res = vertexList[randNextNode].vertex;
    nowIndex = randNextNode;    
    while (true) {
      if (vertexList[nowIndex].edgeList.size() == 0) {
        break;
      } else {
        randNextNode = random.nextInt(vertexList[nowIndex].edgeList.size());
        final EdgeNode veTemp = (EdgeNode) vertexList[nowIndex].edgeList.get(randNextNode);
        if (veTemp.visited == true) {
          break;
        } else {
          res = res + " " + veTemp.endWord;
          
          final EdgeNode newTemp = new EdgeNode();
          newTemp.startVer = veTemp.startVer;
          newTemp.startWord = veTemp.startWord;
          newTemp.endVer =  veTemp.endVer;
          newTemp.endWord = veTemp.endWord;
          newTemp.weight = veTemp.weight;
          newTemp.visited = true;
          vertexList[nowIndex].edgeList.set(randNextNode, newTemp);
          nowIndex = veTemp.endVer;
        }
      }
      
    }
    
    final GraphViz gviz = new GraphViz();
    gviz.addln(gviz.start_graph());
    gviz.addln("size = \"15,4.3\"");
    gviz.addln("edge [color=black]");
    //填充dot语言代码
    //gviz.addln("A -> B;");
    for (int i = 0; i < this.numofvertex; i++) {
      for (int j = 0; j < this.vertexList[i].edgeList.size(); j++) {
        final EdgeNode veTemp = (EdgeNode) vertexList[i].edgeList.get(j);
        gviz.addln(veTemp.startWord + " -> " + veTemp.endWord + ";");
      }
    }
    
    gviz.addln("edge [color=blue]");
    
    final String[] tempRes = res.trim().split("\\s+");
    
    for (int i = 0; i < tempRes.length - 1; i ++) {
      gviz.addln(tempRes[i] + "->" + tempRes[i+1] + "[label=\"step " + String.valueOf(i + 1) + "\"]" + ";");
    }
    
    gviz.addln(gviz.end_graph());
    
    
    gviz.increaseDpi();
    final String type = "png";
    final String repesentationType= "dot";
    final File out1 = new File("../Lab1/src/RandWalk." + type);    // Windows
    gviz.writeGraphToFile(gviz.getGraph(gviz.getDotSource(), type, repesentationType), out1);
    
    return res;
    
  }
}
