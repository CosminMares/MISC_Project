package sas.misc.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import sas.misc.server.ServerPeer;
import sas.misc.structs.Message;
import sas.misc.structs.PrivateMessage;

public class Utils {

	static String receivedMessage;
	static Thread messageThread;
	static Thread privateMessageThread;
	String stringLength20 = "amcdloedlcfrodscvghy";
	String stringLengthUp20 = "amcdloedlcfrodscvghyp";
	
	public Utils() {
//		pipeOut = new PipedOutputStream();
//		System.setOut(new PrintStream(pipeOut));
	}

	public void serverFake(int port) {

		messageThread = new Thread() {
			public void run() {
				try {
					ServerSocket myServer = new ServerSocket(port);
					Socket s = myServer.accept();
					System.out.println("ServerStub - accepted connection");
					Message m = null;
					while (m == null) {
						try {
							ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
							m = (Message) ois.readObject();
							System.out.println(m.toString());
							if (m.toString().length() != 0) {
								Utils.receivedMessage = m.toString();
								s.close();
								myServer.close();
								this.interrupt();
							}
						} catch (IOException | ClassNotFoundException ex) {
						}
					}
				} catch (IOException e) {
				}
			}
		};
		messageThread.start();
	}
	
	public void serverFakePrivateMessage(int port) {

		privateMessageThread = new Thread() {
			public void run() {
				try {
					ServerSocket myServer = new ServerSocket(port);
					Socket s = myServer.accept();
					System.out.println("ServerStub - accepted connection");
					PrivateMessage m = null;
					while (m == null) {
						try {
							ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
							m = (PrivateMessage) ois.readObject();
							System.out.println(m.toString());
							if (m.toString().length() != 0) {
								Utils.receivedMessage = m.toString();
								s.close();
								myServer.close();
								this.interrupt();
							}
						} catch (IOException | ClassNotFoundException ex) {
						}
					}
				} catch (IOException e) {
				}
			}
		};
		privateMessageThread.start();
	}

	public Socket socketFake(String address, int port) {
		Socket s = null;
		try {
			s = new Socket(address, port);
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
		return s;
	}

//	public void readConsoleLastLine() throws IOException {
//		String c = "";
//		PipedInputStream pipeIn = new PipedInputStream(this.pipeOut);
//
//
//		Scanner sc = new Scanner(pipeIn);
//		while (!c.equalsIgnoreCase("Quit")) {
//			c = sc.nextLine();
//		}
//		String lastLine = c;
//	}
	
	public String get1100Characters() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 55; i++) {
			sb.append(stringLength20);
		}
		return sb.toString();
	}
}
