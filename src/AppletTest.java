import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.*;
//applet version
public class AppletTest extends JApplet {
	private static String host, port, username;
	private static JFrame frm;
	private static boolean conclose = false, chatopen = false;
	
	public void init(){
		frm = new JFrame();
		frm.setTitle("Connection interface");
		frm.setLayout(null);
		final JTextField userInput = new JTextField();
		userInput.setText("xxx.x.x.x");
		userInput.addFocusListener(new FocusAdapter(){
			public void focusGained(FocusEvent ev){
				SwingUtilities.invokeLater(new Runnable(){
					public void run() {
						userInput.selectAll();
					}					
				});
			}
		});
		userInput.setBounds(112, 30, 100, 20);
		frm.add(userInput);
		final JTextField inputPort = new JTextField();
		inputPort.setText("100");
		inputPort.addFocusListener(new FocusAdapter(){
			public void focusGained(FocusEvent ev){
				SwingUtilities.invokeLater(new Runnable(){
					public void run() {
						inputPort.selectAll();
					}					
				});
			}
		});
		inputPort.setBounds(112, 110, 100, 20);
		frm.add(inputPort);
		final JTextField txtUname = new JTextField();
		txtUname.setText("Anonymous User");
		txtUname.addFocusListener(new FocusAdapter(){
			public void focusGained(FocusEvent ev){
				SwingUtilities.invokeLater(new Runnable(){
					public void run() {
						txtUname.selectAll();
					}					
				});
			}
		});
		txtUname.setBounds(112, 190, 100, 20);
		frm.add(txtUname);
		JButton btn = new JButton();
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				host = userInput.getText();
				port = inputPort.getText();
				username = txtUname.getText();
				conclose = true;
			}
		});
		
		JButton btn2 = new JButton();
		btn2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				System.exit(0);
			}
		});
		
		JLabel lblIP = new JLabel();
		lblIP.setText("Connect to: ");
		lblIP.setBounds(12, 30, 100, 20);
		JLabel lblPort = new JLabel();
		lblPort.setText("Port #: ");
		lblPort.setBounds(12, 110, 100, 20);
		JLabel lblUser = new JLabel();
		lblUser.setText("Username: ");
		lblUser.setBounds(12, 190, 100, 20);
		frm.add(lblIP);
		frm.add(lblPort);
		frm.add(lblUser);
		
		btn.setText("Connect");
		btn2.setText("Exit");
		btn.setBounds(10, 270, 120, 20);
		btn2.setBounds(150, 270, 120, 20);
		frm.add(btn2);
		frm.add(btn);
		frm.setSize(300, 350);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setVisible(true);
		
		while(!chatopen){
			if(conclose){
				frm.dispose();
				startCli();
				chatopen = false;
			}
		}
	}
	
	private static void startCli(){
		Client cli = new Client(host, port, username);
		cli.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cli.startRunning();
	}
}
