package textprocessnew;

public class EdgeNode{
	public int startVer;	//起点的索引
	public int endVer;		//终点的索引
	public String startWord;	//起点的单词
	public String endWord;		//终点的单词
	
	public boolean visited = false;

	public int weight;	 //边的权重
}
