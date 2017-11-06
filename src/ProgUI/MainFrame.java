package ProgUI;

// ������Ӧswing��awt��
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import textprocessnew.*;


public class MainFrame {
	//������
	protected JFrame bottomFrame = new JFrame("Lab1");
	
	//����
	protected JPanel buttonBar = new JPanel();
	protected JPanel textBar = new JPanel();
	
	//�ļ�ѡ����
	protected JFileChooser txtFileChooser = new JFileChooser(new File("../Lab1/src"));
	
	//������ʾͼƬ�ı�ǩ
	protected JLabel dispPic = new JLabel();
	
	//��ǩ
	protected JLabel progName = new JLabel();
	
	//�ı���
	protected JTextArea progOutput = new JTextArea(4, 75);
	
	
	//��ť���
	protected JButton btnOpenFile = new JButton("���ļ�");
	
	protected JButton btnShowDirectedGraph = new JButton("չʾ����ͼ");
	
	protected JButton btnSearchBridgeWord = new JButton("��ѯ�ŽӴ�");
	
	protected JButton btnCreateNewTxt = new JButton("�������ı�");
	
	protected JButton btnCalShortPath = new JButton("�������·��");
	
	protected JButton btnRandWalk = new JButton("�������");
	
	protected JButton btnCalOneNodeShortPath = new JButton("��Դ���·��");
	
	protected JButton btnCalTwoNodeAllShortPath = new JButton("�����������·��");
	
	protected JButton btnHelp = new JButton("����");
	
	//�����ļ�·��
	protected String picPath = "../Lab1/src/"; 
	protected String txtPath = "";
	
	//Ҫ��ʾ��ͼƬ
	protected Icon iconTemp = new ImageIcon(picPath + "bg1.jpg");
	protected ImageIcon image;
	
	//����ͼ��ʵ��
	protected AdjGraph dirGraph;
	
	
	//���ڲ���ķ�ʽʵ�ּ�����
	
	//Ϊ���ļ���ťʵ�ּ�����
	class LisnOpenFile implements ActionListener{
		public void actionPerformed(ActionEvent s){
			
			txtFileChooser.showOpenDialog(bottomFrame);
			File txtInputFile = txtFileChooser.getSelectedFile();
			String txtContent = new String();
			String procedtxtContent = new String();
			String[] wordList;
			
			
			//����ļ��򿪳ɹ����
			if (txtInputFile == null){
				JOptionPane.showMessageDialog(bottomFrame, "�ļ���ʧ��", "����", 
						JOptionPane.ERROR_MESSAGE);
				progOutput.append("  �ļ���ʧ�ܣ��������������ԣ�\n");
				progOutput.setCaretPosition(progOutput.getText().length());
				return;
			}
			//���ú�����ȡ�ļ�����
			try {
				txtContent = readTxtFile(txtInputFile);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//���ļ����ݽ��д������ҽ�������ͼ
			progOutput.append("\n  �ļ��򿪳ɹ������ڴ�����������ͼ�����Ժ�...");
			progOutput.setCaretPosition(progOutput.getText().length());
			procedtxtContent = txtContent.replaceAll("[^a-z]", " ").replaceAll("\\s+", " ").trim();
			
			progOutput.append("\n  ���򿪵��ļ�����Ϊ��\n  " + procedtxtContent);
			progOutput.setCaretPosition(progOutput.getText().length());
			
			wordList = procedtxtContent.split(" ");
			
			dirGraph = new AdjGraph(wordList);
			
			//����ͼƬ
			dirGraph.showDirectedGraph();
			progOutput.append("\n  ���������ļ��Ѿ���������ͼ����������·��/Lab1/src�£��ҵ�DirectedGraph.png�ļ��鿴��ֱ�ӵ����ť�鿴��");
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
		
		
		//��ȡ�ļ������ݣ����浽һ���ַ����в�����
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
	
	//Ϊչʾ����ͼ��ťʵ�ּ�����
	class LisnShowDirGraph implements ActionListener{
		public void actionPerformed(ActionEvent s){
			try {
				image = new ImageIcon(ImageIO.read(new File(picPath + "DirectedGraph.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			dispPic.setIcon(image);
			dispPic.setText("");
			progOutput.append("\n  ����ͼ�Ѵ򿪣�");
			progOutput.setCaretPosition(progOutput.getText().length());
		}
	}
	
	//Ϊ��ѯ�ŽӴʰ�ťʵ�ּ�����
	class LisnSearchBridgeWord implements ActionListener{
		public void actionPerformed(ActionEvent s){
			String searchBW1 = JOptionPane.showInputDialog(bottomFrame, 
					"������Ҫ��ѯ�����������еĵ�һ���ʣ�\n", "��ѯ�ŽӴ�", 
					JOptionPane.PLAIN_MESSAGE);
			if (searchBW1 == null ){
				return;
			}
			String searchBW2 = JOptionPane.showInputDialog(bottomFrame, 
					"������ڶ����ʣ�\n", "��ѯ�ŽӴ�", 
					JOptionPane.PLAIN_MESSAGE);
			if (searchBW2 == null){
				return;
			}
			
			progOutput.append("\n  ��ѯ" + searchBW1 + "��" + searchBW2 + "֮����ŽӴʣ���ѯ�����Ժ�...");
			progOutput.setCaretPosition(progOutput.getText().length());
			
			String res = dirGraph.queryBridgeWords(searchBW1.toLowerCase().trim(), searchBW2.toLowerCase().trim());
			
			progOutput.append("\n  ��ѯ�����" + res);
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
			
			JOptionPane.showMessageDialog(bottomFrame, res, "��ѯ���", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	
	//Ϊ�������ı���ťע�������
	class LisnCreateNewTxt implements ActionListener{
		public void actionPerformed(ActionEvent s){
			String inputTxt = JOptionPane.showInputDialog(bottomFrame, 
					"������Ҫ���µ��ı���\n", "�������ı�", JOptionPane.PLAIN_MESSAGE);
			if(inputTxt == null){
				return ;
			}
			
			progOutput.append("\n  ��ѡ�����������ı���");
			progOutput.append("\n  �������ˣ�" + inputTxt);
			progOutput.setCaretPosition(progOutput.getText().length());
			
			String res = dirGraph.generateNewText(inputTxt);
			
			progOutput.append("\n  �����ɵ��ı��ǣ�" + res);
			progOutput.setCaretPosition(progOutput.getText().length());
			
			JOptionPane.showMessageDialog(bottomFrame, res, "���ɽ��", JOptionPane.PLAIN_MESSAGE);
		}
	}
			
	//Ϊ�������·����ťע�������
	class LisnCalShortPath implements ActionListener{
		public void actionPerformed(ActionEvent s){
			String word1 = JOptionPane.showInputDialog(bottomFrame, 
					"������·������㣺\n", "�������·��", JOptionPane.PLAIN_MESSAGE);
			
			if(word1 == null){
				return;
			}
			
			while(dirGraph.hasVertex(word1) == false){
				if(dirGraph.hasVertex(word1) == false){
					JOptionPane.showMessageDialog(bottomFrame, "���" + word1 + "���ڶ�����У�", "����", JOptionPane.ERROR_MESSAGE);
					word1 = JOptionPane.showInputDialog(bottomFrame, 
							"������·������㣺\n", "�������·��", JOptionPane.PLAIN_MESSAGE);
					if(word1 == null){
						return;
					}
				}
			}
			
			String word2 = JOptionPane.showInputDialog(bottomFrame, 
					"������·�����յ㣺\n", "�������·��", JOptionPane.PLAIN_MESSAGE);
			
			if(word2 == null){
				return;
			}
			
			while(dirGraph.hasVertex(word2) == false){
				if(dirGraph.hasVertex(word2) == false){
					JOptionPane.showMessageDialog(bottomFrame, "�յ�" + word2 + "���ڶ�����У�", "����", JOptionPane.ERROR_MESSAGE);
					
					word2 = JOptionPane.showInputDialog(bottomFrame, 
							"������·�����յ㣺\n", "�������·��", JOptionPane.PLAIN_MESSAGE);
					if(word2 == null){
						return;
					}
				}
			}
			
			String res = dirGraph.calcShortestPath(word1, word2);
			
			if(res == "���ɴ�"){
				progOutput.append("\n  " + res);
				progOutput.setCaretPosition(progOutput.getText().length());
				JOptionPane.showMessageDialog(bottomFrame, res, "������", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				progOutput.append("\n  " + res);
				progOutput.setCaretPosition(progOutput.getText().length());
				progOutput.append("\n  ���·���Ѿ���ͼ���ú��߱������������·��/Lab1/src�£��ҵ�OneShortestPath.png�ļ��鿴��");
				progOutput.setCaretPosition(progOutput.getText().length());
				
				try {
					image = new ImageIcon(ImageIO.read(new File(picPath + "OneShortestPath.png")));
				} catch (IOException e) {
					e.printStackTrace();
				}
				dispPic.setIcon(image);
				dispPic.setText("");
				
				JOptionPane.showMessageDialog(bottomFrame, res, "������", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
			
	//Ϊ������߰�ťע�������
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
			
			JOptionPane.showMessageDialog(bottomFrame, "������߽��Ϊ��" + res + "���ѱ�����/Lab1/src�µ�RandWalk.txt�С�", 
					"���", JOptionPane.PLAIN_MESSAGE);
			progOutput.append("\n  ������߽��Ϊ��" + res);
			progOutput.setCaretPosition(progOutput.getText().length());
		}
	}
	
	//Ϊ��Դ���·����ťע�������
	class LisnCalOneNodeShortPath implements ActionListener{
		public void actionPerformed(ActionEvent s){
			String word1 = JOptionPane.showInputDialog(bottomFrame, 
					"������·������㣺\n", "���㵥Դ���·��", JOptionPane.PLAIN_MESSAGE);
			if(word1 == null){
				return;
			}
			
			while(dirGraph.hasVertex(word1) == false){	
				JOptionPane.showMessageDialog(bottomFrame, "���" + word1 + "���ڶ�����У�", "����", JOptionPane.ERROR_MESSAGE);
				word1 = JOptionPane.showInputDialog(bottomFrame, 
							"������·������㣺\n", "�������·��", JOptionPane.PLAIN_MESSAGE);
				if(word1 == null){
					return;
				}
			}
			
			String[] options = dirGraph.getEndNodeOptions(word1).split("\\s+");
			String[] res = dirGraph.ShowOneSourcePath(word1);
			
			String allRes;
			if(res[0].equals("���ɴ�")){
				allRes = word1 + "��" + options[0] + res[0];
			}
			else{
				allRes = word1 + "��" + options[0] + "�����·��Ϊ��" + res[0];
			}
			
			for(int i = 1; i < res.length; i++){
				if(res[i].equals("���ɴ�")){
					allRes = allRes + "\n" + word1 + "��" + options[i] + res[i];
				}
				else{
					allRes = allRes + "\n" + word1 + "��" + options[i] + "�����·��Ϊ��" + res[i];
				}
			}
			
			JOptionPane.showMessageDialog(bottomFrame, allRes, "���", JOptionPane.PLAIN_MESSAGE);
			
			progOutput.append("\n  " + allRes);
			progOutput.setCaretPosition(progOutput.getText().length());
			
		}
	}
	
	//Ϊ�����������·����ťע�������
	class LisnCalTwoNodeAllShortPath implements ActionListener{
		public void actionPerformed(ActionEvent s){
			String word1 = JOptionPane.showInputDialog(bottomFrame, 
					"������·������㣺\n", "����������������·��", JOptionPane.PLAIN_MESSAGE);
			
			if(word1 == null){
				return;
			}
			
			while(dirGraph.hasVertex(word1) == false){
				if(dirGraph.hasVertex(word1) == false){
					JOptionPane.showMessageDialog(bottomFrame, "���" + word1 + "���ڶ�����У�", "����", JOptionPane.ERROR_MESSAGE);
					word1 = JOptionPane.showInputDialog(bottomFrame, 
							"������·������㣺\n", "����������������·��", JOptionPane.PLAIN_MESSAGE);
					if(word1 == null){
						return;
					}
				}
			}
			
			String word2 = JOptionPane.showInputDialog(bottomFrame, 
					"������·�����յ㣺\n", "����������������·��", JOptionPane.PLAIN_MESSAGE);
			
			if(word2 == null){
				return;
			}
			
			while(dirGraph.hasVertex(word2) == false){
				if(dirGraph.hasVertex(word2) == false){
					JOptionPane.showMessageDialog(bottomFrame, "�յ�" + word2 + "���ڶ�����У�", "����", JOptionPane.ERROR_MESSAGE);
					
					word2 = JOptionPane.showInputDialog(bottomFrame, 
							"������·�����յ㣺\n", "����������������·��", JOptionPane.PLAIN_MESSAGE);
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
				
				JOptionPane.showMessageDialog(bottomFrame, word1 + "��" + word2 + "����������·������ͼ������ɫ�����", 
						"���", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(bottomFrame, word1 + "��" + word2 + "���ɴ", 
						"���", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	//Ϊ������ťע�������
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
			
			
			JOptionPane.showMessageDialog(bottomFrame, helpContent, "����", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	
	//���캯��
	public MainFrame(){
		//����·��
		File ftemp = new File(picPath);
		ftemp.mkdirs();
		
		//���ڴ�С�����޸�,ʹ�þ��Բ���
		bottomFrame.setSize(800, 720);
		bottomFrame.setResizable(false);
		bottomFrame.setLayout(null);
		
		//�������Ʊ�ǩ
		progName.setText("ͼ  ��  ת  ��");
		progName.setFont(new Font("����", Font.BOLD, 32));
		progName.setForeground(Color.BLUE);
		progName.setHorizontalAlignment(JLabel.CENTER);
		
		
		dispPic.setText("����ͼ���ڴ���ʾ��");
		dispPic.setFont(new Font("����", Font.BOLD, 40));
		//dispPic.setIcon(iconTemp);
		dispPic.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		dispPic.setHorizontalAlignment(JLabel.CENTER);
		
		//�ڰ�ť������Ӱ�ť
		buttonBar.setLayout(null);
		
		buttonBar.add(btnOpenFile);
		btnOpenFile.setBounds(20, 30, 120, 40);
		btnOpenFile.setFont(new Font("����", Font.BOLD, 14));
		
		buttonBar.add(btnShowDirectedGraph);
		btnShowDirectedGraph.setBounds(20, 100, 120, 40);
		btnShowDirectedGraph.setFont(new Font("����", Font.BOLD, 14));
		
		buttonBar.add(btnSearchBridgeWord);
		btnSearchBridgeWord.setBounds(20, 170, 120, 40);
		btnSearchBridgeWord.setFont(new Font("����", Font.BOLD, 14));
		
		buttonBar.add(btnCreateNewTxt);
		btnCreateNewTxt.setBounds(20, 240, 120, 40);
		btnCreateNewTxt.setFont(new Font("����", Font.BOLD, 14));
		
		buttonBar.add(btnCalShortPath);
		btnCalShortPath.setBounds(20, 310, 120, 40);
		btnCalShortPath.setFont(new Font("����", Font.BOLD, 13));
		
		buttonBar.add(btnRandWalk);
		btnRandWalk.setBounds(20, 380, 120, 40);
		btnRandWalk.setFont(new Font("����", Font.BOLD, 14));
		
		buttonBar.add(btnCalOneNodeShortPath);
		btnCalOneNodeShortPath.setBounds(20, 450, 120, 40);
		btnCalOneNodeShortPath.setFont(new Font("����", Font.BOLD, 12));
		
		buttonBar.add(btnCalTwoNodeAllShortPath);
		btnCalTwoNodeAllShortPath.setBounds(20, 520, 120, 40);
		btnCalTwoNodeAllShortPath.setFont(new Font("����", Font.BOLD, 10));
		
		
		//���ð�����ť
		btnHelp.setFont(new Font("����", Font.PLAIN, 12));
		
		//����ı��򣬲���Ϊ�ı�����ӹ�����
		textBar.add(new JScrollPane(progOutput));
		progOutput.setLineWrap(true);
		progOutput.setWrapStyleWord(true);
		progOutput.setCaretPosition(progOutput.getText().length());
		
		//�ı����м����ʼ����
		progOutput.setText("  ��ӭʹ�ã�\n  ������Ӧ�İ�ť��ɲ���������ͼ�������Ϸ���ʾ��\n");
		
		btnShowDirectedGraph.setEnabled(false);
		btnSearchBridgeWord.setEnabled(false);
		btnCreateNewTxt.setEnabled(false);
		btnCalShortPath.setEnabled(false);
		btnRandWalk.setEnabled(false);
		btnCalOneNodeShortPath.setEnabled(false);
		btnCalTwoNodeAllShortPath.setEnabled(false);
		
		//Ϊ��ťע�������
		//Ϊ���ļ���ťע�������
		LisnOpenFile lisBtnOpenFile = new LisnOpenFile();
		btnOpenFile.addActionListener(lisBtnOpenFile);
		
		//Ϊչʾ����ͼ��ťע�������
		LisnShowDirGraph lisBtnShowDirGraph = new LisnShowDirGraph();
		btnShowDirectedGraph.addActionListener(lisBtnShowDirGraph);
		
		//Ϊ��ѯ�ŽӴʰ�ťע�������
		LisnSearchBridgeWord lisBtnSearchBridgeWord = new LisnSearchBridgeWord();
		btnSearchBridgeWord.addActionListener(lisBtnSearchBridgeWord); 
		
		//Ϊ�������ı���ťע�������
		LisnCreateNewTxt lisbtnCreateNewTxt = new LisnCreateNewTxt();
		btnCreateNewTxt.addActionListener(lisbtnCreateNewTxt);
		
		//Ϊ�������·����ťע�������
		LisnCalShortPath lisbtnCalShortPath = new LisnCalShortPath();
		btnCalShortPath.addActionListener(lisbtnCalShortPath);
		
		//Ϊ������߰�ťע�������
		LisnRandWalk lisbtnRandWalk = new LisnRandWalk();
		btnRandWalk.addActionListener(lisbtnRandWalk);
		
		//Ϊ��Դ���·����ťע�������
		LisnCalOneNodeShortPath lisbtnCalOneNodeShortPath = new LisnCalOneNodeShortPath();
		btnCalOneNodeShortPath.addActionListener(lisbtnCalOneNodeShortPath);
		
		//Ϊ�����������·����ťע�������
		LisnCalTwoNodeAllShortPath lisbtnCalTwoNodeAllShortPath = new LisnCalTwoNodeAllShortPath();
		btnCalTwoNodeAllShortPath.addActionListener(lisbtnCalTwoNodeAllShortPath);
		
		//Ϊ������ťע�������
		LisnHelp lisbtnHelp = new LisnHelp();
		btnHelp.addActionListener(lisbtnHelp);
		
		//Ϊ�������
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


