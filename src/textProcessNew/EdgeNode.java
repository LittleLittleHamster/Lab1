package textprocessnew;

public class EdgeNode{
	private int startVer;	//��������
	private int endVer;		//�յ������
	private String startWord;	//���ĵ���
	private String endWord;		//�յ�ĵ���
	
	private boolean visited;

	private int weight;	 //�ߵ�Ȩ��

    public int getStartVer() {
        return startVer;
    }

    public void setStartVer(int startVer) {
        this.startVer = startVer;
    }
    

    public int getEndVer() {
        return endVer;
    }

    public void setEndVer(int endVer) {
        this.endVer = endVer;
    }

    public String getStartWord() {
        return startWord;
    }

    public void setStartWord(String startWord) {
        this.startWord = startWord;
    }

    public String getEndWord() {
        return endWord;
    }

    public void setEndWord(String endWord) {
        this.endWord = endWord;
    }

    public boolean getVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
	
	
}
