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
		this.setTitle("客户端");
		this.setBounds(0, 0, 1366, 768);
		this.setLayout(null);
		
		//标签空间
		lblIcon = new JLabel();
		lblIcon.setBounds(0, 0, 1366, 768);
		
		//图标
		ImageIcon icon = new ImageIcon("D:/Koala.jpg");
		lblIcon.setIcon(icon);
		this.add(lblIcon);
		this.setVisible(true);
	}
	
	/**
	 * 更新图标
	 */
	public void updateIcon(byte[] dataBytes){
		ImageIcon icon = new ImageIcon(dataBytes);
		lblIcon.setIcon(icon);
	}
}