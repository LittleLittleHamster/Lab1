
package textprocessnew;

import java.util.*;

import gviz.GraphViz;

import java.io.File;

import java.util.Random;

public class AdjGraph {
	private int NumOfVertex;
	
	private VertexNode[] vertexList;
	private int[][] vertexMatrix;
	private List collectList = new ArrayList();
	
	private String pathTemp = new String();
	
	private int[][] dist;
	private int[][][] midList;

	
	
	public int getNumOfVertex() {
        return NumOfVertex;
    }

    public void setNumOfVertex(int numOfVertex) {
        NumOfVertex = numOfVertex;
    }

    public VertexNode[] getVertexList() {
        return vertexList;
    }

    public void setVertexList(VertexNode[] vertexList) {
        this.vertexList = vertexList;
    }

    public int[][] getVertexMatrix() {
        return vertexMatrix;
    }

    public void setVertexMatrix(int[][] vertexMatrix) {
        this.vertexMatrix = vertexMatrix;
    }

    public List getCollectList() {
        return collectList;
    }

    public void setCollectList(List collectList) {
        this.collectList = collectList;
    }

    public String getPathTemp() {
        return pathTemp;
    }

    public void setPathTemp(String pathTemp) {
        this.pathTemp = pathTemp;
    }

    public int[][] getDist() {
        return dist;
    }

    public void setDist(int[][] dist) {
        this.dist = dist;
    }

    public int[][][] getMidList() {
        return midList;
    }

    public void setMidList(int[][][] midList) {
        this.midList = midList;
    }
    
    
    
    

    //根据传入的字符串数组，构建有向图
	public AdjGraph(String[] wordList){
		//字符串数组去重
		for(int i = 0; i < wordList.length; i++){
			if(!collectList.contains(wordList[i])){
				collectList.add(wordList[i]);
			}
		}
		
		NumOfVertex = collectList.size();
		
		vertexList = new VertexNode[NumOfVertex];
		
		//构造顶点表
		for(int i = 0; i < NumOfVertex; i++){
		    VertexNode verTmp = new VertexNode();
		    verTmp.setVertex((String) collectList.get(i));
		    verTmp.setEdgeList(new ArrayList());
			vertexList[i] = verTmp;
		}
		
		//构造边表
		for(int i = 0; i < wordList.length - 1; i++){
			EdgeNode tempEdgeNode = new EdgeNode();
			int indexVertexNode = collectList.indexOf(wordList[i]);
			if(vertexList[indexVertexNode].getEdgeList().size() == 0){
				tempEdgeNode.setStartVer(indexVertexNode);
				tempEdgeNode.setEndVer(collectList.indexOf(wordList[i+1]));
				tempEdgeNode.setStartWord(wordList[i]);
				tempEdgeNode.setEndWord(wordList[i+1]);
				tempEdgeNode.setWeight(1);
				tempEdgeNode.setVisited(false);
				
				ArrayList edgeListTmp = vertexList[indexVertexNode].getEdgeList();
				edgeListTmp.add(tempEdgeNode);
				vertexList[indexVertexNode].setEdgeList(edgeListTmp);
			}
			else{
				byte eFlag = 0;
				for(int j = 0; j < vertexList[indexVertexNode].getEdgeList().size(); j++){
					EdgeNode veTemp = (EdgeNode) vertexList[indexVertexNode].getEdgeList().get(j);
					if(veTemp.getEndWord().equals(wordList[i+1])){
						eFlag = 1;
						EdgeNode NewEdgeNode = new EdgeNode();
						NewEdgeNode.setStartVer(veTemp.getStartVer());
						NewEdgeNode.setStartWord(veTemp.getStartWord());
						NewEdgeNode.setEndVer(veTemp.getEndVer());
						NewEdgeNode.setEndWord(veTemp.getEndWord());
						NewEdgeNode.setWeight(veTemp.getWeight()+1);
						NewEdgeNode.setVisited(veTemp.getVisited());
						
						ArrayList<EdgeNode> edgeListTmp = vertexList[indexVertexNode].getEdgeList();
		                edgeListTmp.set(j, NewEdgeNode);
						
						vertexList[indexVertexNode].setEdgeList(edgeListTmp);;
						break;
					}
				}
				if(eFlag == 0){
					EdgeNode NewEdgeNode = new EdgeNode();
					
					NewEdgeNode.setStartVer(indexVertexNode);
					NewEdgeNode.setStartWord(wordList[i]);
					NewEdgeNode.setEndVer(collectList.indexOf(wordList[i+1]));
					NewEdgeNode.setEndWord(wordList[i+1]);
					NewEdgeNode.setWeight(1);
					NewEdgeNode.setVisited(false);
					
					ArrayList<EdgeNode> edgeListTmp = vertexList[indexVertexNode].getEdgeList();
                    edgeListTmp.add(NewEdgeNode);
                    vertexList[indexVertexNode].setEdgeList(edgeListTmp);
					
				}
			}
		}
		
		for(int i = 0; i < NumOfVertex; i++){
			vertexList[i].getEdgeList().trimToSize();
		}
		
		AdjlistToAdjMatrix();
		
		modFloyd();
	}
	
	//生成有向图
	public void showDirectedGraph(){
		File f = new File("../Lab1/src/DirectedGraph.png");
		f.delete();
		GraphViz gv = new GraphViz();
		gv.addln(gv.start_graph());
		gv.addln("size = \"15,4.3\"");
		//填充dot语言代码
		//gv.addln("A -> B;");
		for(int i = 0; i < this.NumOfVertex; i++){
			for(int j = 0; j < this.vertexList[i].getEdgeList().size(); j++){
				EdgeNode veTemp = (EdgeNode) vertexList[i].getEdgeList().get(j);
				gv.addln(veTemp.getStartWord() + " -> " + veTemp.getEndWord() +
						" [style=bold,label=\"" + String.valueOf(veTemp.getWeight()) + "\"];");
			}
		}
		
		gv.addln(gv.end_graph());
		gv.increaseDpi();
		String type = "png";
		String repesentationType= "dot";
		File out1 = new File("../Lab1/src/DirectedGraph." + type);    // Windows
		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type, repesentationType), out1);
	}
	
	//查询桥接词
	public String queryBridgeWords(String word1, String word2){
		ArrayList bridgeWord = new ArrayList();
		String[] bridgeWordArray;
		String res;
		
		int indexWord1;
		int indexWord2;
		
		if(word1.equals("") || word2.equals("")) {
            return "存在空单词，请重新输入！";
        }
        if(word1.matches("[a-zA-Z]+") == false || word2.matches("[a-zA-Z]+") == false) {
            return "包含非字母字符，请重新输入！";
        }
		
		if(collectList.contains(word1) == false && collectList.contains(word2) == true){
			res = "No \"" + word1 + "\" in the Graph!";
			return res;
		}
		if(collectList.contains(word1) == true && collectList.contains(word2) == false){
			res = "No \"" + word2 + "\" in the Graph!";
			return res;
		}
		if(collectList.contains(word1) == false && collectList.contains(word2) == false){
			res = "No \"" + word1 + "\" and \"" + word2 + "\" in the Graph!";
			return res;
		}
		
		indexWord1 = collectList.indexOf(word1);
		indexWord2 = collectList.indexOf(word2);
		
		for(int i = 0; i < vertexList[indexWord1].getEdgeList().size(); i++){
			EdgeNode veTemp = (EdgeNode) vertexList[indexWord1].getEdgeList().get(i);
			if(hasEdge(veTemp.getEndWord(), word2) == true){
				bridgeWord.add(veTemp.getEndWord());
			}
		}
		
		if(bridgeWord.size() == 0){
			res = "No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!";
			return res;
		}
		
		bridgeWord.trimToSize();
		
		bridgeWordArray = new String[bridgeWord.size()];
		for(int i = 0; i < bridgeWord.size(); i++){
			bridgeWordArray[i] = (String) bridgeWord.get(i);
		}
		
		
		if (bridgeWordArray.length == 1){
			res = "The bridge words from \"" + word1 + "\" to \"" + word2 + "\" is: " + bridgeWordArray[0] + ".";
			
			
			GraphViz gv = new GraphViz();
			gv.addln(gv.start_graph());
			gv.addln("size = \"15,4.3\"");
			gv.addln("edge [color=black]");
			//填充dot语言代码
			//gv.addln("A -> B;");
			for(int i = 0; i < this.NumOfVertex; i++){
				for(int j = 0; j < this.vertexList[i].getEdgeList().size(); j++){
					EdgeNode veTemp = (EdgeNode) vertexList[i].getEdgeList().get(j);
					gv.addln(veTemp.getStartWord() + " -> " + veTemp.getEndWord() +
							" [style=bold,label=\"" + String.valueOf(veTemp.getWeight()) + "\"];");
				}
			}
			
			gv.addln(bridgeWordArray[0] + "[shape=box];");
			
			gv.addln(gv.end_graph());
			
			gv.increaseDpi();
			String type = "png";
			String repesentationType= "dot";
			File out1 = new File("../Lab1/src/BridgeWord." + type);    // Windows
			gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type, repesentationType), out1);
			
			
			return res;
		}
		else{
			res = "The bridge words from \"" + word1 + "\" to \"" + word2 + "\" are: ";
			
			
			GraphViz gv = new GraphViz();
			gv.addln(gv.start_graph());
			gv.addln("size = \"15,4.3\"");
			gv.addln("edge [color=black]");
			//填充dot语言代码
			//gv.addln("A -> B;");
			for(int i = 0; i < this.NumOfVertex; i++){
				for(int j = 0; j < this.vertexList[i].getEdgeList().size(); j++){
					EdgeNode veTemp = (EdgeNode) vertexList[i].getEdgeList().get(j);
					gv.addln(veTemp.getStartWord() + " -> " + veTemp.getEndWord() +
							" [style=bold,label=\"" + String.valueOf(veTemp.getWeight()) + "\"];");
				}
			}			
			
			for(int i = 0; i < bridgeWordArray.length - 2; i++){
				res = res + bridgeWordArray[i] + ", ";
				gv.addln(bridgeWordArray[i] + " [shape=box];");
			}
			res = res + bridgeWordArray[bridgeWordArray.length - 2] + " and " + bridgeWordArray[bridgeWordArray.length - 1] + ".";
			
			for(int i = 0; i < bridgeWordArray.length; i++){
				gv.addln(bridgeWordArray[i] + " [shape=box];");
			}
			
			gv.addln(gv.end_graph());
			gv.increaseDpi();
			String type = "png";
			String repesentationType= "dot";
			File out1 = new File("../Lab1/src/BridgeWord." + type);    // Windows
			gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type, repesentationType), out1);
			
			return res;
		}
	}
	
	//生成新文本
	public String generateNewText(String inputText){
		String willProcTxt = inputText;
		String[] inputTxtArray = willProcTxt.split("\\s+");
		
		String res = inputTxtArray[0];
		
		Random random = new Random();
		
		for(int i = 0; i < inputTxtArray.length - 1; i++){
			String SearchRes = queryBridgeWords(
			        inputTxtArray[i].toLowerCase().replaceAll("[^a-z]", " ").trim(), 
					inputTxtArray[i+1].toLowerCase().replaceAll("[^a-z]", " ").trim());
			if(SearchRes.startsWith("No")){
				res = res + " " + inputTxtArray[i+1];
			}
			else{
				String[] brgWord = SearchRes.split(":")[1].replaceAll("and", " ")
				        .replaceAll("[^a-z]", " ").trim().split("\\s+");
				if(brgWord.length == 1){
					res = res + " " + brgWord[0] + " " + inputTxtArray[i+1];
				}
				else{
					int randres = random.nextInt(brgWord.length);
					res = res + " " + brgWord[randres] + " " + inputTxtArray[i+1];
				}
			}
		}
		return res;
	}
	
	//计算两点之间的最短路径
	public String calcShortestPath(String word1, String word2){
		String strPath = showShortestPath(word1, word2, false);
		
		if(strPath != "不可达"){
			String res = "路径为：" + strPath + "；路径长度为：" + String.valueOf(dist[collectList.indexOf(word1)][collectList.indexOf(word2)]);
			
			GraphViz gv = new GraphViz();
			gv.addln(gv.start_graph());
			gv.addln("size = \"15,4.3\"");
			gv.addln("edge [color=black]");
			//填充dot语言代码
			//gv.addln("A -> B;");
			for(int i = 0; i < this.NumOfVertex; i++){
				for(int j = 0; j < this.vertexList[i].getEdgeList().size(); j++){
					EdgeNode veTemp = (EdgeNode) vertexList[i].getEdgeList().get(j);
					gv.addln(veTemp.getStartWord() + " -> " + veTemp.getEndWord() +
							" [style=bold,label=\"" + String.valueOf(veTemp.getWeight()) + "\"];");
				}
			}
			
			gv.addln("edge [color=red]");
			gv.addln(strPath);
			
			gv.addln(gv.end_graph());
			gv.increaseDpi();
			System.out.println(gv.getDotSource());
			String type = "png";
			String repesentationType= "dot";
			File out1 = new File("../Lab1/src/OneShortestPath." + type);    // Windows
			gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type, repesentationType), out1);
			
			return res;
		}
		else{
			return "不可达";
		}
		
	}
	
	//随机游走，返回随机游走的字符串
	public String randomWalk(){
		//遍历所有的边，初始化
		for(int i = 0; i < NumOfVertex; i++){
			if(vertexList[i].getEdgeList().size() > 0){
				for(int j = 0; j < vertexList[i].getEdgeList().size(); j++){
					EdgeNode veTemp = (EdgeNode) vertexList[i].getEdgeList().get(j);
					EdgeNode newTemp = new EdgeNode();
					newTemp.setStartVer(veTemp.getStartVer());
					newTemp.setStartWord(veTemp.getStartWord());
					newTemp.setEndVer(veTemp.getEndVer());
					newTemp.setEndWord(veTemp.getEndWord());
					newTemp.setWeight(veTemp.getWeight());
					newTemp.setVisited(false);
					
					ArrayList<EdgeNode> edgeListTmp = vertexList[i].getEdgeList();
                    edgeListTmp.set(j, newTemp);
                    
                    vertexList[i].setEdgeList(edgeListTmp);
				}
			}
		}
		Random random = new Random();
		int randNextNode = random.nextInt(NumOfVertex);
		int nowIndex;
		String res = vertexList[randNextNode].getVertex();
		nowIndex = randNextNode;
		
		while(true){
			if(vertexList[nowIndex].getEdgeList().size() == 0){
				break;
			}
			else{
				randNextNode = random.nextInt(vertexList[nowIndex].getEdgeList().size());
				EdgeNode veTemp = (EdgeNode) vertexList[nowIndex].getEdgeList().get(randNextNode);
				if(veTemp.getVisited() == true){
					break;
				}
				else{
					res = res + " " + veTemp.getEndWord();
					
					EdgeNode newTemp = new EdgeNode();
					newTemp.setStartVer(veTemp.getStartVer());
					newTemp.setStartWord(veTemp.getStartWord());
					newTemp.setEndVer(veTemp.getEndVer());
					newTemp.setEndWord(veTemp.getEndWord());
					newTemp.setWeight(veTemp.getWeight());
					newTemp.setVisited(true);
					
					ArrayList edgeListTmp = vertexList[nowIndex].getEdgeList();
					edgeListTmp.set(randNextNode, newTemp);
					
					vertexList[nowIndex].setEdgeList(edgeListTmp);
					
					nowIndex = veTemp.getEndVer();
				}
			}
			
		}
		
		GraphViz gv = new GraphViz();
		gv.addln(gv.start_graph());
		gv.addln("size = \"15,4.3\"");
		gv.addln("edge [color=black]");
		//填充dot语言代码
		//gv.addln("A -> B;");
		for(int i = 0; i < this.NumOfVertex; i++){
			for(int j = 0; j < this.vertexList[i].getEdgeList().size(); j++){
				EdgeNode veTemp = (EdgeNode) vertexList[i].getEdgeList().get(j);
				gv.addln(veTemp.getStartWord() + " -> " + veTemp.getEndWord() +";");
			}
		}
		
		gv.addln("edge [color=blue]");
		
		String[] tempRes = res.trim().split("\\s+");
		
		for(int i = 0; i < tempRes.length - 1; i ++){
			gv.addln(tempRes[i] + "->" + tempRes[i+1] + "[label=\"step " + String.valueOf(i + 1) + "\"]" + ";");
		}
		
		gv.addln(gv.end_graph());
		
		
		gv.increaseDpi();
		String type = "png";
		String repesentationType= "dot";
		File out1 = new File("../Lab1/src/RandWalk." + type);    // Windows
		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type, repesentationType), out1);
		
		return res;
		
	}
	
	//返回起点之外的其他顶点
	public String getEndNodeOptions(String word1){
		String res = " ";
		for(int i = 0; i < collectList.size(); i++){
			if(!collectList.get(i).equals(word1)){
				res = res + collectList.get(i) + " ";
			}
		}
		return res.trim();
	}
	
	//返回单源最短路径
	public String[] ShowOneSourcePath(String word1){
		String[] options = getEndNodeOptions(word1).split("\\s+");
		String[] res = new String[options.length];
		for(int i = 0; i < options.length; i++){
			if(showShortestPath(word1, options[i], false).equals("不可达")){
				res[i] = showShortestPath(word1, options[i], false);
			}
			else{
				res[i] = showShortestPath(word1, options[i], false) + ";路径长度为：" 
						+ String.valueOf(dist[collectList.indexOf(word1)][collectList.indexOf(options[i])]);
			}
			}
			
		return res;
	}
	
	//计算两点之间所有最短路径
	public boolean showTowNodeAllShortPath(String word1, String word2){
		GraphViz gv = new GraphViz();
		gv.addln(gv.start_graph());
		gv.addln("size = \"15,4.3\"");
		gv.addln("edge [color=black]");
		//填充dot语言代码
		//gv.addln("A -> B;");
		for(int i = 0; i < this.NumOfVertex; i++){
			for(int j = 0; j < this.vertexList[i].getEdgeList().size(); j++){
				EdgeNode veTemp = (EdgeNode) vertexList[i].getEdgeList().get(j);
				gv.addln(veTemp.getStartWord() + " -> " + veTemp.getEndWord() +
						" [style=bold,label=\"" + String.valueOf(veTemp.getWeight()) + "\"];");
			}
		}
		
		gv.addln("edge [color=green]");
		
		boolean canArrive = showAllPath(gv, word1, word2);
		
		gv.addln(gv.end_graph());
		
		gv.increaseDpi();
		String type = "png";
		String repesentationType= "dot";
		File out1 = new File("../Lab1/src/TwoNodeAllShortestPath." + type);    // Windows
		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type, repesentationType), out1);
		
		return canArrive;
	}
	
	//判断一个词是否在顶点表
	public boolean hasVertex(String word){
		if(collectList.contains(word)){
			return true;
		}
		else{
			return false;
		}
	}
	
	//内部方法
	//两个顶点之间是否有边
	private boolean hasEdge(String word1, String word2){
		int indexWord1;
		int indexWord2;
		indexWord1 = collectList.indexOf(word1);
		indexWord2 = collectList.indexOf(word2);
		for(int i = 0; i < vertexList[indexWord1].getEdgeList().size(); i++){
			EdgeNode veTemp = (EdgeNode) vertexList[indexWord1].getEdgeList().get(i);
			if(veTemp.getEndWord().equals(word2)){
				return true;
			}
		}
		return false;
	}
	
	//将邻接表转换为邻接矩阵
	private void AdjlistToAdjMatrix(){
		vertexMatrix = new int[NumOfVertex][NumOfVertex];
		//初始化邻接矩阵
		for(int i = 0; i < NumOfVertex; i++){
			for(int j = 0; j < NumOfVertex; j++){
				vertexMatrix[i][j] = 10000;
			}
		}
		
		for(int i = 0; i < NumOfVertex; i++){
			for(int j = 0; j < vertexList[i].getEdgeList().size(); j++){
				EdgeNode veTemp = (EdgeNode) vertexList[i].getEdgeList().get(j);
				vertexMatrix[veTemp.getStartVer()][veTemp.getEndVer()] = veTemp.getWeight();
			}
		}
	}
	
	//使用改进的Floyd算法计算最短路径
	private void modFloyd(){
			//首先进行初始化操作
			dist = new int[NumOfVertex][NumOfVertex];
			for(int i = 0; i < NumOfVertex; i++){
				for(int j = 0; j < NumOfVertex; j++){
					dist[i][j] = vertexMatrix[i][j];
				}
			}
			
			midList = new int[NumOfVertex][NumOfVertex][NumOfVertex];
			for(int i = 0; i < NumOfVertex; i++){
				for(int j = 0; j < NumOfVertex; j++){
					for(int k = 0; k < NumOfVertex; k++){
						midList[i][j][k] = -1;
					}
				}
			}
			
			//执行改进的Floyd算法
			for(int k = 0; k < NumOfVertex; k++){
				for(int i = 0; i < NumOfVertex; i++){
					for(int j = 0; j < NumOfVertex;j++){
						if(dist[i][j] > dist[i][k] + dist[k][j])
						{
							dist[i][j] = dist[i][k] + dist[k][j];
							for(int l = 0; l < NumOfVertex; l++){
								midList[i][j][l] = -1;
							}
							midList[i][j][0] = k;
						}
						else if(dist[i][j] == dist[i][k] + dist[k][j]){
							for(int l = 0; l < NumOfVertex; l++){
								if(midList[i][j][l] == -1){
									midList[i][j][l] = k;
									break;
								}
							}
						}
					}
				}
			}
		}
	
	//展示最短路径
	private String showShortestPath(String word1, String word2, boolean showAll){
		if(showAll == false){
			int startNode = collectList.indexOf(word1);
			int endNode = collectList.indexOf(word2);
			String res;
			if(dist[startNode][endNode] < 10000){
				res = word1 + getPath(startNode, endNode) + "->" + word2;
			}
			else{
				res = "不可达";
			}
			return res;
		}
		else{
			return "已显示在图中！";
		}
	}
	
	//读取路径
	private String getPath(int startNode, int endNode){
		if(midList[startNode][endNode][0] != -1 && midList[startNode][endNode][0] != startNode && midList[startNode][endNode][0] != endNode){
			return getPath(startNode, midList[startNode][endNode][0]) +
					"->" + collectList.get((int) midList[startNode][endNode][0]) + 
					getPath((int) midList[startNode][endNode][0], endNode);
		}
		else if(midList[startNode][endNode][0] == endNode){
			return "->" + collectList.get(endNode);
		}
		else{
			return "";
		}
	}
	
	//显示两点间所有最短路径
	private boolean showAllPath(GraphViz gv, String word1, String word2){
		int startNode = collectList.indexOf(word1);
		int endNode = collectList.indexOf(word2);
		pathTemp = new String();
		
		if(dist[startNode][endNode] < 10000){
			getAllPath(startNode, endNode);
			String[] pathArray = pathTemp.split(";");
			List collectPath = new ArrayList();
			for(int i = 0; i < pathArray.length; i++){
				if(!collectPath.contains(pathArray[i])){
					collectPath.add(pathArray[i]);
				}
			}
			for(int i = 0; i < collectPath.size(); i++){
				gv.addln(collectPath.get(i) + ";");
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	private void getAllPath(int startNode, int endNode){
		int count;
		for(count = 0; count < NumOfVertex; count++){
			if(midList[startNode][endNode][count] == -1){
				break;
			}
		}
		if(count == 0){
			pathTemp = pathTemp + vertexList[startNode].getVertex() + " -> " + vertexList[endNode].getVertex() +";";
			//gv.addln(vertexList[startNode].vertex + " -> " + vertexList[endNode].vertex +";");
			return;
		}
		for(int i = 0; i < count; i++){
			getAllPath(startNode, midList[startNode][endNode][i]); 
			getAllPath(midList[startNode][endNode][i], endNode);
			
		}
	}
}
