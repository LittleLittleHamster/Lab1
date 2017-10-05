package newLab1;
import java.util.Vector;

public class AdjGraph {
	int n,e;
	VertexNode[] vexlist;
	
	public AdjGraph() {
		
	}
	public void nCreate(int n) {     //��ʼ��n
		this.n = n;
		this.vexlist = new VertexNode[n];     
	}
	public void vexCreate(int i,String vertex) {    //���춥���
		this.vexlist[i] = new VertexNode(vertex);
	}
	public void adjGraph(Vector<String>node) {
		EdgeNode p;
		int flag = 0,index = 0;
		for(int i = 0;i < this.n;i++){
			for(int j = 0;j+1 < node.size();j++) {
				if(this.vexlist[i].vertex.equals(node.get(j))) {   //�̶����㣬ɨ�赥����
					for(int k = 0;k < n;k++) {
						if(this.vexlist[k].vertex.equals(node.get(j+1))) {    //A->B�ҳ�B�ڶ���������
							index = k;
							break;
						}
					}
					p = this.vexlist[i].firstedge;
					flag = 0;
					while(p != null) {
						if(p.adjvex == index) {
							p.weight += 1;
							flag = 1;
						}                                            //�ж��Ƿ��Ѿ�����߱�������Ȩ��
						p = p.next;
					}
					if(flag == 0) {           //����߱�
						insert(index,i);
					}
				}
			}
		}
	}
	public void insert(int index,int i) {
		EdgeNode p = null;
		p = this.vexlist[i].firstedge;
		this.vexlist[i].firstedge = new EdgeNode(index);
		this.vexlist[i].firstedge.next = p;
	}
	public String[] queryBridgeWords(String word1,String word2) {
		int flag1 = 100,flag2 =100,k = 2;
		String[] string = new String[100];
		EdgeNode p,q;
		for(int i =0;i < n;i++) {
			if(this.vexlist[i].vertex.equals(word1)) {
				flag1 = i;
			}
			if(this.vexlist[i].vertex.equals(word2)) {
				flag2 = i;
			}
		}
		string[0] = "1";
		string[1] = "1";
		if(flag1 == 100 || flag2 == 100) { //��ǵ����Ƿ����
			if(flag1 == 100) {
				string[0] = "0";
			}
			if(flag2 == 100) {
				string[1] = "0";
			}
			return string;
		}
		p = this.vexlist[flag1].firstedge;
		while(p != null) {
			q = this.vexlist[p.adjvex].firstedge;
			while(q != null) {
				if(q.adjvex == flag2) {
					string[k] = this.vexlist[p.adjvex].vertex;    //�����ŽӴ�
					k++;
				}
				q = q.next;
			}
			p = p.next;
		}
		return string;
	}
	public String generateNewText(String inputText) {
		String[] newText,requery;
		StringBuffer newStringBu = new StringBuffer();
		newText = inputText.split(" ");
		int k = 0;
		int len = newText.length;
		for(int i = 0; i+1 < len;i++) {
			requery = queryBridgeWords(newText[i],newText[i+1]);
			newStringBu.append(newText[i]);
			newStringBu.append(" ");
			for(int j = 2;j < 100;j++) {
				if(requery[j] != null) {       //�ж��Ƿ����ŽӴ�
					k++;
				}
			}
			if(requery[0].equals("1") && requery[1].equals("1") && k != 0 ) {
				newStringBu.append(requery[2]); 
				newStringBu.append(" ");//!!!!!!!!!!�ŽӴ�û���ѡ��
			}
		}
		int i = 1;
		int p = 0;
		newStringBu.append(newText[len-1]);
		String newString = new String(newStringBu);
		return newString;
		//2.7
	}
}
