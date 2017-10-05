package newLab1;

public class EdgeNode{
	int adjvex;  //ÁÚ½ÓµãÓò
	EdgeNode next;
	int weight;

	public EdgeNode(int adjvex) {
		this.adjvex = adjvex;
		this.weight = 1;
		this.next = null;
	}
}