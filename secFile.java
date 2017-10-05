package newLab1;
import java.util.Vector;
public class secFile {
	public secFile() {
		
	}
	public Vector<String> creatString(String[] alh,int t,AdjGraph Adj) {
		String[] newAlh;
		Vector<String> wholeAlh= new Vector<String>();  //用户的输入除去特殊字符 
		String[] newAlhtemp = new String[100];
		int k = 0,m = 0,flag = 0;
		System.out.println(k);
<<<<<<< HEAD
		System.out.println(m);
=======
>>>>>>> C4
		for (int i = 0 ;i < t;i++) {
			alh[i] = alh[i].replaceAll("[~!@#$%^&*()?<>,.]", " ");
		}
		for(int i = 0;i < t;i++) {           //确定单词种类
			newAlh = null;
			newAlh = alh[i].split("\\s+");
			for(int j = 0;j < newAlh.length;j++) {
				flag = 0;
				for(k = 0;k < m;k++){
					if(newAlhtemp[k].equals(newAlh[j])) {
						flag = 1;
					}
				}
				if(flag == 0) {
					newAlhtemp[m] = newAlh[j];
					m++;
				}
			}
			for(int j = 0;j < newAlh.length;j++) {
				wholeAlh.add(newAlh[j]);
			}
		}
		int i = 0;
		while(newAlhtemp[i] != null) {   //确定定点数
			i++;
		}
		Adj.nCreate(i);
		for(int j = 0;j < i;j++) {
			Adj.vexCreate(j,newAlhtemp[j]);  //构造顶点表
		}
		return wholeAlh;
	}
}
