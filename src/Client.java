import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Client extends JFrame{
	private JTextField userInput;
	private String uName;
	private JTextArea chatWin;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String msg = "";
	private String serverIP, serverPort;
	private String defInputText = "Type message here, hit Enter to send message.";
	private Socket conn;
	private boolean _connected = false;//hack job? got around the annoying ass NP Error.

	// constructor here
	public Client(String hostIP, String port, String username) {
		super("Client Instant Messenger");
		setResizable(false);
		
		addWindowListener(new WindowListener(){
			
			public void windowClosing(WindowEvent e){
				sendMessage("END");
				System.exit(0);
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		userInput = new JTextField();
			serverIP = hostIP;
			serverPort = port;
			uName = username;
			userInput.setEditable(false);
			userInput.setText(defInputText);
			userInput.addFocusListener(new FocusListener(){
				@Override
				public void focusGained(FocusEvent arg0) {
					if(userInput.getText().equals(defInputText))
						userInput.setText("");
				}

				@Override
				public void focusLost(FocusEvent arg0) {
					if(userInput.getText().isEmpty())
						userInput.setText(defInputText);
				}
				
			});
			userInput.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					sendMessage(ev.getActionCommand());
					userInput.setText("");
				}
			});
			add(userInput, BorderLayout.SOUTH);
			chatWin = new JTextArea();
			chatWin.setEditable(false);
			chatWin.setWrapStyleWord(true);
			chatWin.setLineWrap(true);
			add(new JScrollPane(chatWin), BorderLayout.CENTER);
			setBounds(250, 200, 500, 350);
			setVisible(true);
	}

	public void startRunning() {
		try {
			connectToServer();
			if(_connected){
				setupStreams();
				whileChatting();
			}
		} catch (EOFException eofe) {
			showMessage("\nClient terminated the connection");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			closeCrap();
		}
	}

	// Connect to server
	private void connectToServer() throws IOException {
		showMessage("Attempting connection...\n");
		try{
			conn = new Socket(InetAddress.getByName(serverIP), Integer.parseInt(serverPort));
			_connected = true;
			showMessage("Connection established to: " + conn.getInetAddress().getHostName()
					+ "\nNOTICE - to end the connect just type the following without quotes: \"END\"");
		}catch(ConnectException ce){
			showMessage("\n\nError: Unable to connect!");
		}		
	}

	// setup streams to send and receive messages
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(conn.getOutputStream());
		output.flush();// always flush!

		input = new ObjectInputStream(conn.getInputStream());
		showMessage("\nReady.\n");
	}

	// while chatting with server
	private void whileChatting() throws IOException {
		ableToType(true);
		do {
			try {
				msg = (String) input.readObject();
				showMessage(msg);
			} catch (ClassNotFoundException e) {
				showMessage("Unknown object type.");
			}
		} while (!(msg.split(":")[1].toUpperCase().equals(" END")) && _connected);
	}

	// close everything
	private void closeCrap() {
		ableToType(false);
		if(_connected){
			try {
				output.close();
				input.close();
				conn.close();
				this.dispose();
				_connected = false;//might be unnecessary
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// send messages
	private void sendMessage(String mess) {
		try {
			if(mess.isEmpty()){}//nothing will happen :p
			else{
				output.writeObject("\n" + uName + ": " + mess);
				output.flush();// ALWAYS flush
				showMessage("\n" + uName + ": " + mess);
			}
		} catch (IOException ex) {
			chatWin.append("\nERROR ");
		}
	}

	// change/update chat window
	private void showMessage(final String mess) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatWin.append(mess);
			}
		});
	}

	// permission to type
	private void ableToType(final boolean val) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				userInput.setEditable(val);
			}
		});
	}	
}
