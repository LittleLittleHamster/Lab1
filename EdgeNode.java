package newLab1;

public class EdgeNode{
	int adjvex;  //�ڽӵ���
	EdgeNode next;
	int weight;
	//�Ҿ������ĸ�
	public EdgeNode(int adjvex) {
		this.adjvex = adjvex;
		this.weight = 1;
		this.next = null;
	}
}