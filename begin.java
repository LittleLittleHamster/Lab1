package newLab1;
import java.util.Vector;
import java.util.Scanner;

public class begin {
	public static void main(String[] args)throws Exception{
		java.io.File file= new java.io.File("C:\\Users\\SD\\Desktop\\test\\abc.txt");
		int i = 0,k = 0;
		Scanner input = new Scanner(file);
		String[] alh = new String[100],queryString = new String[100];
		Vector<String> node= new Vector<String>();
		while(input.hasNext()) {
			String words = input.nextLine();
			alh[i] = words.toLowerCase();
			i++;
		}
		input.close();
		secFile sfile = new secFile();
		AdjGraph Adj = new AdjGraph();
		node = sfile.creatString(alh,i,Adj);
		Adj.adjGraph(node);
		
		Scanner input1 = new Scanner(System.in);
		Scanner input2 = new Scanner(System.in);
		String word1 = input1.next();
		String word2 = input2.next();
		queryString = Adj.queryBridgeWords(word1, word2);
		if(queryString[0].equals("0") ||queryString[1].equals("0")) {  //查询桥接词
			if(queryString[0].equals("0") && queryString[1].equals("0")) {
				System.out.println("No "+"\""+word1+"\" and " +"\""+word2+"\" in the graph!");
			}
			else if(queryString[0].equals("0")) {
				System.out.println("No "+"\""+word1+"\" in the graph!");
			}
			else {
				System.out.println("No "+"\""+word2+"\" in the graph!");
			}
		}
		else {
			for(int j = 2;j < 100;j++) {
				if(queryString[j] != null) {
					k++;
				}
			}
			if(k == 0) {
				System.out.println("No bridge words from "+"\""+word1+"\" to "+"\""+word2+"\"!");
			}
			else if(k ==1) {
				System.out.println("The bridge words from "+"\""+word1+"\" to "+"\""+word2+"\" is: "+queryString[2]);
			}
			else if(k>1) {
				System.out.print("The bridge words from "+"\""+word1+"\" to "+"\""+word2+"\" are: ");
				for(int j = 2;j < k+2;j++) {
					if(j < k+1) {
						System.out.print(queryString[j]+",");
					}
					else {
						System.out.println(" and "+queryString[j]+".");
					}
				}
			}
		}
		//更新文本
		Scanner input3 = new Scanner(System.in);
		word1 = input3.nextLine();
		
		System.out.print(Adj.generateNewText(word1));
	}
}
