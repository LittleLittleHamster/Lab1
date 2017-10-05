package newLab1;

public class EdgeNode{
	int adjvex;  //邻接点域
	EdgeNode next;
	int weight;
	//我就是随便改改
	public EdgeNode(int adjvex) {
		this.adjvex = adjvex;
		this.weight = 1;
		this.next = null;
	}
}