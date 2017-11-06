package ProgUI;

// 引入相应swing和awt库
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import textprocessnew.*;


public class MainFrame {
	//主窗体
	protected JFrame bottomFrame = new JFrame("Lab1");
	
	//容器
	protected JPanel buttonBar = new JPanel();
	protected JPanel textBar = new JPanel();
	
	//文件选择器
	protected JFileChooser txtFileChooser = new JFileChooser(new File("../Lab1/src"));
	
	//用于演示图片的标签
	protected JLabel dispPic = new JLabel();
	
	//标签
	protected JLabel progName = new JLabel();
	
	//文本域
	protected JTextArea progOutput = new JTextArea(4, 75);
	
	
	//按钮组件
	protected JButton btnOpenFile = new JButton("打开文件");
	
	protected JButton btnShowDirectedGraph = new JButton("展示有向图");
	
	protected JButton btnSearchBridgeWord = new JButton("查询桥接词");
	
	protected JButton btnCreateNewTxt = new JButton("生成新文本");
	
	protected JButton btnCalShortPath = new JButton("计算最短路径");
	
	protected JButton btnRandWalk = new JButton("随机游走");
	
	protected JButton btnCalOneNodeShortPath = new JButton("单源最短路径");
	
	protected JButton btnCalTwoNodeAllShortPath = new JButton("两点所有最短路径");
	
	protected JButton btnHelp = new JButton("帮助");
	
	//设置文件路径
	protected String picPath = "../Lab1/src/"; 
	protected String txtPath = "";
	
	//要显示的图片
	protected Icon iconTemp = new ImageIcon(picPath + "bg1.jpg");
	protected ImageIcon image;
	
	//有向图的实例
	protected AdjGraph dirGraph;
	
	
	//以内部类的方式实现监听器
	
	//为打开文件按钮实现监听器
	class LisnOpenFile implements ActionListener{
		public void actionPerformed(ActionEvent s){
			
			txtFileChooser.showOpenDialog(bottomFrame);
			File txtInputFile = txtFileChooser.getSelectedFile();
			String txtContent = new String();
			String procedtxtContent = new String();
			String[] wordList;
			
			
			//检查文件打开成功与否
			if (txtInputFile == null){
				JOptionPane.showMessageDialog(bottomFrame, "文件打开失败", "错误", 
						JOptionPane.ERROR_MESSAGE);
				progOutput.append("  文件打开失败，请您更正后重试！\n");
				progOutput.setCaretPosition(progOutput.getText().length());
				return;
			}
			//调用函数获取文件内容
			try {
				txtContent = readTxtFile(txtInputFile);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//对文件内容进行处理，并且建立有向图
			progOutput.append("\n  文件打开成功，正在处理并生成有向图，请稍候...");
			progOutput.setCaretPosition(progOutput.getText().length());
			procedtxtContent = txtContent.replaceAll("[^a-z]", " ").replaceAll("\\s+", " ").trim();
			
			progOutput.append("\n  您打开的文件内容为：\n  " + procedtxtContent);
			progOutput.setCaretPosition(progOutput.getText().length());
			
			wordList = procedtxtContent.split(" ");
			
			dirGraph = new AdjGraph(wordList);
			
			//生成图片
			dirGraph.showDirectedGraph();
			progOutput.append("\n  根据您的文件已经生成有向图，您可以在路径/Lab1/src下，找到DirectedGraph.png文件查看或直接点击按钮查看！");
			progOutput.setCaretPosition(progOutput.getText().length());
			
			btnOpenFile.setEnabled(true);
			btnShowDirectedGraph.setEnabled(true);
			btnSearchBridgeWord.setEnabled(true);
			btnCreateNewTxt.setEnabled(true);
			btnCalShortPath.setEnabled(true);
			btnRandWalk.setEnabled(true);
			btnCalOneNodeShortPath.setEnabled(true);
			btnCalTwoNodeAllShortPath.setEnabled(true);
		}
		
		
		//读取文件的内容，保存到一个字符串中并返回
		private String readTxtFile(File txtFile) throws Exception{
			Scanner txtInput = new Scanner(txtFile);
			String txtTemp = new String();
			while(txtInput.hasNext()){
				txtTemp = txtTemp + " " + txtInput.nextLine();
			}
			txtInput.close();
			return txtTemp.toLowerCase();
		}
		
	}
	
	//为展示有向图按钮实现监听器
	class LisnShowDirGraph implements ActionListener{
		public void actionPerformed(ActionEvent s){
			try {
				image = new ImageIcon(ImageIO.read(new File(picPath + "DirectedGraph.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			dispPic.setIcon(image);
			dispPic.setText("");
			progOutput.append("\n  有向图已打开！");
			progOutput.setCaretPosition(progOutput.getText().length());
		}
	}
	
	//为查询桥接词按钮实现监听器
	class LisnSearchBridgeWord implements ActionListener{
		public void actionPerformed(ActionEvent s){
			String searchBW1 = JOptionPane.showInputDialog(bottomFrame, 
					"请输入要查询的两个单词中的第一个词：\n", "查询桥接词", 
					JOptionPane.PLAIN_MESSAGE);
			if (searchBW1 == null ){
				return;
			}
			String searchBW2 = JOptionPane.showInputDialog(bottomFrame, 
					"请输入第二个词：\n", "查询桥接词", 
					JOptionPane.PLAIN_MESSAGE);
			if (searchBW2 == null){
				return;
			}
			
			progOutput.append("\n  查询" + searchBW1 + "和" + searchBW2 + "之间的桥接词，查询中请稍候...");
			progOutput.setCaretPosition(progOutput.getText().length());
			
			String res = dirGraph.queryBridgeWords(searchBW1.toLowerCase().trim(), searchBW2.toLowerCase().trim());
			
			progOutput.append("\n  查询结果：" + res);
			progOutput.setCaretPosition(progOutput.getText().length());
			
			if(!res.startsWith("No")){
				try {
					image = new ImageIcon(ImageIO.read(new File(picPath + "BridgeWord.png")));
				} catch (IOException e) {
					e.printStackTrace();
				}
				dispPic.setIcon(image);
				dispPic.setText("");
			}
			
			JOptionPane.showMessageDialog(bottomFrame, res, "查询结果", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	
	//为生成新文本按钮注册监听器
	class LisnCreateNewTxt implements ActionListener{
		public void actionPerformed(ActionEvent s){
			String inputTxt = JOptionPane.showInputDialog(bottomFrame, 
					"请输入要更新的文本：\n", "生成新文本", JOptionPane.PLAIN_MESSAGE);
			if(inputTxt == null){
				return ;
			}
			
			progOutput.append("\n  您选择了生成新文本！");
			progOutput.append("\n  您输入了：" + inputTxt);
			progOutput.setCaretPosition(progOutput.getText().length());
			
			String res = dirGraph.generateNewText(inputTxt);
			
			progOutput.append("\n  新生成的文本是：" + res);
			progOutput.setCaretPosition(progOutput.getText().length());
			
			JOptionPane.showMessageDialog(bottomFrame, res, "生成结果", JOptionPane.PLAIN_MESSAGE);
		}
	}
			
	//为计算最短路径按钮注册监听器
	class LisnCalShortPath implements ActionListener{
		public void actionPerformed(ActionEvent s){
			String word1 = JOptionPane.showInputDialog(bottomFrame, 
					"请输入路径的起点：\n", "计算最短路径", JOptionPane.PLAIN_MESSAGE);
			
			if(word1 == null){
				return;
			}
			
			while(dirGraph.hasVertex(word1) == false){
				if(dirGraph.hasVertex(word1) == false){
					JOptionPane.showMessageDialog(bottomFrame, "起点" + word1 + "不在顶点表中！", "错误", JOptionPane.ERROR_MESSAGE);
					word1 = JOptionPane.showInputDialog(bottomFrame, 
							"请输入路径的起点：\n", "计算最短路径", JOptionPane.PLAIN_MESSAGE);
					if(word1 == null){
						return;
					}
				}
			}
			
			String word2 = JOptionPane.showInputDialog(bottomFrame, 
					"请输入路径的终点：\n", "计算最短路径", JOptionPane.PLAIN_MESSAGE);
			
			if(word2 == null){
				return;
			}
			
			while(dirGraph.hasVertex(word2) == false){
				if(dirGraph.hasVertex(word2) == false){
					JOptionPane.showMessageDialog(bottomFrame, "终点" + word2 + "不在顶点表中！", "错误", JOptionPane.ERROR_MESSAGE);
					
					word2 = JOptionPane.showInputDialog(bottomFrame, 
							"请输入路径的终点：\n", "计算最短路径", JOptionPane.PLAIN_MESSAGE);
					if(word2 == null){
						return;
					}
				}
			}
			
			String res = dirGraph.calcShortestPath(word1, word2);
			
			if(res == "不可达"){
				progOutput.append("\n  " + res);
				progOutput.setCaretPosition(progOutput.getText().length());
				JOptionPane.showMessageDialog(bottomFrame, res, "计算结果", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				progOutput.append("\n  " + res);
				progOutput.setCaretPosition(progOutput.getText().length());
				progOutput.append("\n  最短路径已经在图中用红线标出，您可以在路径/Lab1/src下，找到OneShortestPath.png文件查看！");
				progOutput.setCaretPosition(progOutput.getText().length());
				
				try {
					image = new ImageIcon(ImageIO.read(new File(picPath + "OneShortestPath.png")));
				} catch (IOException e) {
					e.printStackTrace();
				}
				dispPic.setIcon(image);
				dispPic.setText("");
				
				JOptionPane.showMessageDialog(bottomFrame, res, "计算结果", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
			
	//为随机游走按钮注册监听器
	class LisnRandWalk implements ActionListener{
		public void actionPerformed(ActionEvent s){
			String res = dirGraph.randomWalk();
			
			FileWriter fw;
			try {
				fw = new FileWriter("../Lab1/src/RandWalk.txt");
				fw.write(res, 0, res.length());
				fw.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				image = new ImageIcon(ImageIO.read(new File(picPath + "RandWalk.png")));
			} catch (IOException e) {

				e.printStackTrace();
			}
			dispPic.setIcon(image);
			dispPic.setText("");
			
			JOptionPane.showMessageDialog(bottomFrame, "随机游走结果为：" + res + "，已保存在/Lab1/src下的RandWalk.txt中。", 
					"结果", JOptionPane.PLAIN_MESSAGE);
			progOutput.append("\n  随机游走结果为：" + res);
			progOutput.setCaretPosition(progOutput.getText().length());
		}
	}
	
	//为单源最短路径按钮注册监听器
	class LisnCalOneNodeShortPath implements ActionListener{
		public void actionPerformed(ActionEvent s){
			String word1 = JOptionPane.showInputDialog(bottomFrame, 
					"请输入路径的起点：\n", "计算单源最短路径", JOptionPane.PLAIN_MESSAGE);
			if(word1 == null){
				return;
			}
			
			while(dirGraph.hasVertex(word1) == false){	
				JOptionPane.showMessageDialog(bottomFrame, "起点" + word1 + "不在顶点表中！", "错误", JOptionPane.ERROR_MESSAGE);
				word1 = JOptionPane.showInputDialog(bottomFrame, 
							"请输入路径的起点：\n", "计算最短路径", JOptionPane.PLAIN_MESSAGE);
				if(word1 == null){
					return;
				}
			}
			
			String[] options = dirGraph.getEndNodeOptions(word1).split("\\s+");
			String[] res = dirGraph.ShowOneSourcePath(word1);
			
			String allRes;
			if(res[0].equals("不可达")){
				allRes = word1 + "到" + options[0] + res[0];
			}
			else{
				allRes = word1 + "到" + options[0] + "的最短路径为：" + res[0];
			}
			
			for(int i = 1; i < res.length; i++){
				if(res[i].equals("不可达")){
					allRes = allRes + "\n" + word1 + "到" + options[i] + res[i];
				}
				else{
					allRes = allRes + "\n" + word1 + "到" + options[i] + "的最短路径为：" + res[i];
				}
			}
			
			JOptionPane.showMessageDialog(bottomFrame, allRes, "结果", JOptionPane.PLAIN_MESSAGE);
			
			progOutput.append("\n  " + allRes);
			progOutput.setCaretPosition(progOutput.getText().length());
			
		}
	}
	
	//为两点所有最短路径按钮注册监听器
	class LisnCalTwoNodeAllShortPath implements ActionListener{
		public void actionPerformed(ActionEvent s){
			String word1 = JOptionPane.showInputDialog(bottomFrame, 
					"请输入路径的起点：\n", "计算两点间所有最短路径", JOptionPane.PLAIN_MESSAGE);
			
			if(word1 == null){
				return;
			}
			
			while(dirGraph.hasVertex(word1) == false){
				if(dirGraph.hasVertex(word1) == false){
					JOptionPane.showMessageDialog(bottomFrame, "起点" + word1 + "不在顶点表中！", "错误", JOptionPane.ERROR_MESSAGE);
					word1 = JOptionPane.showInputDialog(bottomFrame, 
							"请输入路径的起点：\n", "计算两点间所有最短路径", JOptionPane.PLAIN_MESSAGE);
					if(word1 == null){
						return;
					}
				}
			}
			
			String word2 = JOptionPane.showInputDialog(bottomFrame, 
					"请输入路径的终点：\n", "计算两点间所有最短路径", JOptionPane.PLAIN_MESSAGE);
			
			if(word2 == null){
				return;
			}
			
			while(dirGraph.hasVertex(word2) == false){
				if(dirGraph.hasVertex(word2) == false){
					JOptionPane.showMessageDialog(bottomFrame, "终点" + word2 + "不在顶点表中！", "错误", JOptionPane.ERROR_MESSAGE);
					
					word2 = JOptionPane.showInputDialog(bottomFrame, 
							"请输入路径的终点：\n", "计算两点间所有最短路径", JOptionPane.PLAIN_MESSAGE);
					if(word2 == null){
						return;
					}
				}
			}
			
			if(dirGraph.showTowNodeAllShortPath(word1, word2)){
			
				try {
					image = new ImageIcon(ImageIO.read(new File(picPath + "TwoNodeAllShortestPath.png")));
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				dispPic.setIcon(image);
				dispPic.setText("");
				
				JOptionPane.showMessageDialog(bottomFrame, word1 + "到" + word2 + "间的所有最短路径已在图中用绿色标出！", 
						"结果", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(bottomFrame, word1 + "到" + word2 + "不可达！", 
						"结果", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	//为帮助按钮注册监听器
	class LisnHelp implements ActionListener{
		public void actionPerformed(ActionEvent s){
			String helpContent = "";
			File helpFile = new File("../Lab1/src/Help.txt");
			try {
				Scanner input = new Scanner(helpFile);
				while(input.hasNextLine()){
					helpContent = helpContent + input.nextLine();
				}
				input.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			JOptionPane.showMessageDialog(bottomFrame, helpContent, "帮助", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	
	//构造函数
	public MainFrame(){
		//创建路径
		File ftemp = new File(picPath);
		ftemp.mkdirs();
		
		//窗口大小不可修改,使用绝对布局
		bottomFrame.setSize(800, 720);
		bottomFrame.setResizable(false);
		bottomFrame.setLayout(null);
		
		//程序名称标签
		progName.setText("图  文  转  换");
		progName.setFont(new Font("楷体", Font.BOLD, 32));
		progName.setForeground(Color.BLUE);
		progName.setHorizontalAlignment(JLabel.CENTER);
		
		
		dispPic.setText("有向图将在此显示！");
		dispPic.setFont(new Font("楷体", Font.BOLD, 40));
		//dispPic.setIcon(iconTemp);
		dispPic.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		dispPic.setHorizontalAlignment(JLabel.CENTER);
		
		//在按钮栏中添加按钮
		buttonBar.setLayout(null);
		
		buttonBar.add(btnOpenFile);
		btnOpenFile.setBounds(20, 30, 120, 40);
		btnOpenFile.setFont(new Font("楷体", Font.BOLD, 14));
		
		buttonBar.add(btnShowDirectedGraph);
		btnShowDirectedGraph.setBounds(20, 100, 120, 40);
		btnShowDirectedGraph.setFont(new Font("楷体", Font.BOLD, 14));
		
		buttonBar.add(btnSearchBridgeWord);
		btnSearchBridgeWord.setBounds(20, 170, 120, 40);
		btnSearchBridgeWord.setFont(new Font("楷体", Font.BOLD, 14));
		
		buttonBar.add(btnCreateNewTxt);
		btnCreateNewTxt.setBounds(20, 240, 120, 40);
		btnCreateNewTxt.setFont(new Font("楷体", Font.BOLD, 14));
		
		buttonBar.add(btnCalShortPath);
		btnCalShortPath.setBounds(20, 310, 120, 40);
		btnCalShortPath.setFont(new Font("楷体", Font.BOLD, 13));
		
		buttonBar.add(btnRandWalk);
		btnRandWalk.setBounds(20, 380, 120, 40);
		btnRandWalk.setFont(new Font("楷体", Font.BOLD, 14));
		
		buttonBar.add(btnCalOneNodeShortPath);
		btnCalOneNodeShortPath.setBounds(20, 450, 120, 40);
		btnCalOneNodeShortPath.setFont(new Font("楷体", Font.BOLD, 12));
		
		buttonBar.add(btnCalTwoNodeAllShortPath);
		btnCalTwoNodeAllShortPath.setBounds(20, 520, 120, 40);
		btnCalTwoNodeAllShortPath.setFont(new Font("楷体", Font.BOLD, 10));
		
		
		//设置帮助按钮
		btnHelp.setFont(new Font("楷体", Font.PLAIN, 12));
		
		//添加文本框，并且为文本框添加滚动条
		textBar.add(new JScrollPane(progOutput));
		progOutput.setLineWrap(true);
		progOutput.setWrapStyleWord(true);
		progOutput.setCaretPosition(progOutput.getText().length());
		
		//文本框中加入初始文字
		progOutput.setText("  欢迎使用！\n  请点击相应的按钮完成操作，有向图将会在上方显示！\n");
		
		btnShowDirectedGraph.setEnabled(false);
		btnSearchBridgeWord.setEnabled(false);
		btnCreateNewTxt.setEnabled(false);
		btnCalShortPath.setEnabled(false);
		btnRandWalk.setEnabled(false);
		btnCalOneNodeShortPath.setEnabled(false);
		btnCalTwoNodeAllShortPath.setEnabled(false);
		
		//为按钮注册监听器
		//为打开文件按钮注册监听器
		LisnOpenFile lisBtnOpenFile = new LisnOpenFile();
		btnOpenFile.addActionListener(lisBtnOpenFile);
		
		//为展示有向图按钮注册监听器
		LisnShowDirGraph lisBtnShowDirGraph = new LisnShowDirGraph();
		btnShowDirectedGraph.addActionListener(lisBtnShowDirGraph);
		
		//为查询桥接词按钮注册监听器
		LisnSearchBridgeWord lisBtnSearchBridgeWord = new LisnSearchBridgeWord();
		btnSearchBridgeWord.addActionListener(lisBtnSearchBridgeWord); 
		
		//为生成新文本按钮注册监听器
		LisnCreateNewTxt lisbtnCreateNewTxt = new LisnCreateNewTxt();
		btnCreateNewTxt.addActionListener(lisbtnCreateNewTxt);
		
		//为计算最短路径按钮注册监听器
		LisnCalShortPath lisbtnCalShortPath = new LisnCalShortPath();
		btnCalShortPath.addActionListener(lisbtnCalShortPath);
		
		//为随机游走按钮注册监听器
		LisnRandWalk lisbtnRandWalk = new LisnRandWalk();
		btnRandWalk.addActionListener(lisbtnRandWalk);
		
		//为单源最短路径按钮注册监听器
		LisnCalOneNodeShortPath lisbtnCalOneNodeShortPath = new LisnCalOneNodeShortPath();
		btnCalOneNodeShortPath.addActionListener(lisbtnCalOneNodeShortPath);
		
		//为两点所有最短路径按钮注册监听器
		LisnCalTwoNodeAllShortPath lisbtnCalTwoNodeAllShortPath = new LisnCalTwoNodeAllShortPath();
		btnCalTwoNodeAllShortPath.addActionListener(lisbtnCalTwoNodeAllShortPath);
		
		//为帮助按钮注册监听器
		LisnHelp lisbtnHelp = new LisnHelp();
		btnHelp.addActionListener(lisbtnHelp);
		
		//为组件布局
		bottomFrame.add(dispPic);
		dispPic.setLocation(30, 80);
		dispPic.setSize(550, 470);
		
		bottomFrame.add(btnHelp);
		btnHelp.setBounds(660, 40, 60, 20);
		
		bottomFrame.add(buttonBar);
		buttonBar.setLocation(610, 80);
		buttonBar.setSize(160, 580);
		
		bottomFrame.add(textBar);
		textBar.setLocation(30, 570);
		textBar.setSize(550, 100);
		
		bottomFrame.add(progName);
		progName.setBounds(10, 20, 780, 40);
		
		bottomFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bottomFrame.setVisible(true);
		
		//txtFileChooser.showOpenDialog(bottomFrame);
	}
}


