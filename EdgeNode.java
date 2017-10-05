package newLab1;

public class EdgeNode{
	int adjvex;  //ÁÚ½ÓµãÓò
	EdgeNode next;
	int weight;
	// Git 2.4
	public EdgeNode(int adjvex) {
		this.adjvex = adjvex;
		this.weight = 1;
		this.next = null;
	}
}