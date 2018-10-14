package com.huihuang.Client;

import javax.swing.*;

/**
 * ClientUI
 */
public class ClientUI extends JFrame {
	
	private JLabel lblIcon ;
	
	public ClientUI(){
		init();
	}

	private void init() {
		this.setTitle("�ͻ���");
		this.setBounds(0, 0, 1366, 768);
		this.setLayout(null);
		
		//��ǩ�ռ�
		lblIcon = new JLabel();
		lblIcon.setBounds(0, 0, 1366, 768);
		
		//ͼ��
		ImageIcon icon = new ImageIcon("D:/Koala.jpg");
		lblIcon.setIcon(icon);
		this.add(lblIcon);
		this.setVisible(true);
	}
	
	/**
	 * ����ͼ��
	 */
	public void updateIcon(byte[] dataBytes){
		ImageIcon icon = new ImageIcon(dataBytes);
		lblIcon.setIcon(icon);
	}
}