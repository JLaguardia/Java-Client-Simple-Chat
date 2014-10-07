import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
public class Messenger {
	private static String host, port, username;
	private static JFrame frm;
	private static boolean conclose = false, chatopen = false;
	
	public static void main(String[] args) {		
		frm = new JFrame();
		frm.setTitle("Connection interface");
		frm.setLayout(null);
		final JTextField userInput = new JTextField();
		userInput.setText("99.72.53.82");
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
		inputPort.setText("13337");
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
		
		//message to joseph and dad for the laughs
		JLabel lblFam = new JLabel();
		lblFam.setText("Yes this is the ip to my laptop.");
		int style = Font.ITALIC;
		lblFam.setFont(new Font ("Courier", style , 10));
		lblFam.setBounds(80, 50, 250, 20);
		frm.add(lblFam);
		
		btn.setText("Connect");
		btn2.setText("Exit");
		btn.setBounds(10, 270, 120, 20);
		btn2.setBounds(150, 270, 120, 20);
		frm.add(btn2);
		frm.add(btn);
		frm.setBounds(250, 200, 300, 350);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setVisible(true);
		
		while(!chatopen){
			if(conclose){
				frm.dispose();
				startCli();
				chatopen = true;
			}
		}
		
	//	Client yoo = new Client("127.0.0.1", "100");
	//	yoo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	yoo.startRunning();
	}
	
	private static void startCli(){
		Client cli = new Client(host, port, username);
		cli.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		cli.startRunning();
	}
	
}
