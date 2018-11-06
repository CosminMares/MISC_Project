package sas.misc.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import sas.misc.client.ClientPeer;
import sas.misc.intefaces.NetworkMessage;
import sas.misc.intefaces.NetworkPrivateMessage;
import sas.misc.server.Server;
import sas.misc.server.exceptions.InvalidContentLengthException;
import sas.misc.server.exceptions.InvalidExpeditorLengthException;
import sas.misc.structs.Message;

public class ClientPeerTest {

	private String userName;
	private String localhost;
	private int port;
	private Socket s;
	private Thread thread;

//	@BeforeClass
//	public static void onlyOnce() {
//		Server.main(null);
//	}

	@Before
	public void setUp() {
		this.thread = new Thread() {
			public void run() {
				Server.main(null);
			}
		};
		thread.start();

		this.userName = "John";
		this.localhost = "127.0.0.1";
		this.port = 9000;
		try {
			Thread.sleep(100);
			s = new Socket(localhost, port);
		} catch (Exception e) {
		}
	}

	@After
	public void after() {
		try {
			if (s != null)
				s.close();
		} catch (IOException e) {
		}
	}

	// test constructor for null
	@Test
	public void constructorTestNotNull() {
		assertNotNull(new ClientPeer(this.userName, this.s));
	}

	// test constructor is userName is set correctly
	@Test
	public void testConstructorUserName() {
		String expected = "John";
		assertEquals(expected, (new ClientPeer(this.userName, this.s).getUserName()));
	}

	// test constructor is socket is set correctly
	@Test
	public void testConstructorSocket() {
		assertEquals(this.s, (new ClientPeer(this.userName, this.s).getSocket()));
	}

	// Error test from Right-BICEP
	@Test
	public void testConstructorException() {
		String userName = new Utils().stringLength20Stub(); // 20 characters string
		// String userName = "";
		try {
			ClientPeer cp = new ClientPeer(userName, this.s);
			fail("Expected an InvalidContentLengthException to be thrown");
		} catch (InvalidContentLengthException e) {
		}
	}

	// Test Boundary from Right-BICEP
	// Range from CORRECT
	@Test
	public void testLowBoundaryForUserName() {
		String userName = "1"; // one character string
		ClientPeer cp = new ClientPeer(userName, this.s);
		if (cp != null)
			assert (true);
	}

	// Test Boundary from Right-BICEP
	// Range from CORRECT
	@Test
	public void testTopBoundaryForUserName() {
		String userName = "ertyuiopasdfghjklzx"; // 19 characters string
		ClientPeer cp = new ClientPeer(userName, this.s);
		if (cp != null)
			assert (true);
	}

	// Test Right (Right-BICEP) for sendMessage(String message) method
	// Reference from CORRECT
	@Category(NetworkMessage.class)
	@Test
	public void testSendMessageFunctionality() throws Exception {
		Utils utils = new Utils();	
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		sb1.append("John: Hello");
		sb1.append(utils.nonPrintableCharacters());
		sb2.append("Hello");
		sb2.append(utils.nonPrintableCharacters());
		String expectedMessage = sb1.toString();
		String message = sb2.toString();
		utils.serverFake(9100);
		Thread.sleep(500);
		Socket newSocket = utils.socketSpy(this.localhost, 9100);
		ClientPeer cp = new ClientPeer(this.userName, newSocket);
		cp.sendMessage(message);
		Thread.sleep(500);
		assertEquals(expectedMessage, Utils.receivedMessage);
	}

	// Test top Interval Error for sendMessage(String message) method
	// Error form Right-BICEP
	@Category(NetworkMessage.class)
	@Test
	public void testTopIntervalErrorSendMessage() throws Exception {
		ClientPeer cp = new ClientPeer(this.localhost, this.s);
		Utils utils = new Utils();
		String toBigMessage = utils.get1100CharactersStub() + 'f'; // 1101 characters string
		try {
			cp.sendMessage(toBigMessage);
			fail("Expected an InvalidContentLengthException to be thrown");
		} catch (InvalidContentLengthException e) {
		}
	}

	// Test low Interval Error for sendMessage(String message) method
	// Error form Right-BICEP
	@Category(NetworkMessage.class)
	@Test
	public void testBottomIntervalErrorSendMessage() throws Exception {
		ClientPeer cp = new ClientPeer(this.localhost, this.s);
		String toShortMessage = "";
		try {
			cp.sendMessage(toShortMessage);
			fail("Expected an InvalidContentLengthException to be thrown");
		} catch (InvalidContentLengthException e) {
		}
	}

	// Test top Boundary (Right-BICEP) for sendMessage(String message) method
	// test for 1100 characters message, Range from CORRECT
	@Category(NetworkMessage.class)
	@Test
	public void testTopBoundarySendMessage() throws Exception {
		ClientPeer cp = new ClientPeer(this.localhost, this.s);
		Utils utils = new Utils();
		String message = utils.get1100CharactersStub();
		try {
			cp.sendMessage(message);
		} catch (InvalidContentLengthException e) {
			fail("No exception was expected");
		}
	}

	// Test low Boundary (Right-BICEP) for sendMessage(String message) method
	// Test for 1 character message, Range from CORRECT
	@Category(NetworkMessage.class)
	@Test
	public void testLowBoundarySendMessage() throws Exception {
		ClientPeer cp = new ClientPeer(this.localhost, this.s);
		String message = "1";
		try {
			cp.sendMessage(message);
		} catch (InvalidContentLengthException e) {
			fail("No exception was expected");
		}
	}

	// Test Right (Right-BICEP) for sendMessage(String message, String recipient)
	// method
	// Reference from CORRECT
	@Category(NetworkPrivateMessage.class)
	@Test
	public void testSendPrivateMessageFunctionality() throws Exception {
		String recipient = "Bob";
		String expectedMessage = "(priv)John: Hello";
		String message = "Hello";
		Utils utils = new Utils();
		utils.serverFakePrivateMessage(9091);
		Thread.sleep(500);
		Socket newSocket = utils.socketSpy(this.localhost, 9091);
		ClientPeer cp = new ClientPeer(this.userName, newSocket);
		cp.sendMessage(message, recipient);
		Thread.sleep(500);
		assertEquals(expectedMessage, Utils.receivedMessage);
	}
	
	//Conformance from CORRECT
	@Category(NetworkPrivateMessage.class)
	@Test
	public void testSendPrivateMessageConformance() throws Exception {
		Utils utils = new Utils();
		String recipient = "Bob";		
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		sb1.append("(priv)John: Hello");
		sb1.append(utils.nonPrintableCharacters());
		sb2.append("Hello");
		sb2.append(utils.nonPrintableCharacters());
		String expectedMessage = sb1.toString();
		String message = sb2.toString();
		utils.serverFakePrivateMessage(9091);
		Thread.sleep(500);
		Socket newSocket = utils.socketSpy(this.localhost, 9091);
		ClientPeer cp = new ClientPeer(this.userName, newSocket);
		cp.sendMessage(message, recipient);
		Thread.sleep(500);
		assertEquals(expectedMessage, Utils.receivedMessage);
	}

	// Test Error (Right-BICEP) at message length for sendMessage(String message,
	// String
	// recipient) method
	@Category(NetworkPrivateMessage.class)
	@Test
	public void testErrorMessageForSendPrivateMessage() throws Exception {
		ClientPeer cp = new ClientPeer(this.localhost, this.s);
		String message = "";
		String recipient = "Bob";
		try {
			cp.sendMessage(message, recipient);
			fail("Expected an InvalidContentLengthException to be thrown");
		} catch (InvalidContentLengthException e) {
		}
	}

	// Test Error (Right-BICEP) at message length for sendMessage(String message,
	// String
	// recipient) method
	@Category(NetworkPrivateMessage.class)
	@Test
	public void testErrorRecipientForSendPrivateMessage() throws Exception {
		ClientPeer cp = new ClientPeer(this.localhost, this.s);
		String message = "Hello";
		String recipient = "";
		try {
			cp.sendMessage(message, recipient);
			fail("Expected an InvalidContentLengthException to be thrown");
		} catch (InvalidContentLengthException e) {
		}
	}

	// Test top Boundary (Right-BICEP) for sendMessage(String message, String
	// recipient) method
	// test for 1100 characters message, Range from CORRECT
	@Category(NetworkPrivateMessage.class)
	@Test
	public void testTopBoundaryMessageSendPrivateMessage() throws Exception {
		ClientPeer cp = new ClientPeer(this.localhost, this.s);
		Utils utils = new Utils();
		String message = utils.get1100CharactersStub();
		String recipient = "Bob";
		try {
			cp.sendMessage(message, recipient);
		} catch (InvalidContentLengthException e) {
			fail("No exception was expected");
		}
	}

	// Test low Boundary (Right-BICEP) for sendMessage(String message, String
	// recipient) method
	// test for 1 character message, Range from CORRECT
	@Category(NetworkPrivateMessage.class)
	@Test
	public void testLowBoundaryMessageSendPrivateMessage() throws Exception {
		ClientPeer cp = new ClientPeer(this.localhost, this.s);
		String message = "1";
		String recipient = "Bob";
		try {
			cp.sendMessage(message, recipient);
		} catch (InvalidContentLengthException e) {
			fail("No exception was expected");
		}
	}

	// Test top Boundary (Right-BICEP) for sendMessage(String message, String
	// recipient) method
	// test for 19 character recipient, Range from CORRECT
	@Category(NetworkPrivateMessage.class)
	@Test
	public void testTopBoundaryRecipientSendPrivateMessage() throws Exception {
		ClientPeer cp = new ClientPeer(this.localhost, this.s);
		String message = "Hello";
		String recipient = "qwertyuiopasdfghjkl"; // 19 characters string
		try {
			cp.sendMessage(message, recipient);
		} catch (InvalidContentLengthException e) {
			fail("No exception was expected");
		}
	}

	// Test low Boundary (Right-BICEP) for sendMessage(String message, String
	// recipient) method
	// test for 1 character recipient, Range from CORRECT
	@Category(NetworkPrivateMessage.class)
	@Test
	public void testLowBoundaryRecipientSendPrivateMessage() throws Exception {
		ClientPeer cp = new ClientPeer(this.localhost, this.s);
		String message = "Hello";
		String recipient = "1";
		try {
			cp.sendMessage(message, recipient);
		} catch (InvalidContentLengthException e) {
			fail("No exception was expected");
		}
	}

	// Test performance (Right-BICEP) for sendMessage(String message) method
	// Reference, Time from CORRECT
	@Category(NetworkMessage.class)
	@Test
	public void performanceTestSendMessage() throws Exception {
		long startTime = System.currentTimeMillis();

		String message = "Hello";
		Utils utils = new Utils();
		utils.serverFake(9090);
		Thread.sleep(500);
		Socket newSocket = utils.socketSpy(this.localhost, 9090);
		ClientPeer cp = new ClientPeer(this.userName, newSocket);
		cp.sendMessage(message);
		long stopTime = System.currentTimeMillis();
		long elapsedTime = (stopTime - startTime) / 1000l;
		System.out.println(elapsedTime == 1 ? elapsedTime + " second" : elapsedTime + " seconds");
	}

	// Test performance (Right-BICEP) for sendMessage(String message, String
	// recipient) method
	// Reference, Time from CORRECT
	@Category(NetworkPrivateMessage.class)
	@Test
	public void performanceTestSendPrivateMessage() throws Exception {
		long startTime = System.currentTimeMillis();

		String message = "Hello";
		String recipient = "Bob";
		Utils utils = new Utils();
		utils.serverFakePrivateMessage(9091);
		Thread.sleep(500);
		Socket newSocket = utils.socketSpy(this.localhost, 9091);
		ClientPeer cp = new ClientPeer(this.userName, newSocket);
		cp.sendMessage(message, recipient);
		long stopTime = System.currentTimeMillis();
		long elapsedTime = (stopTime - startTime) / 1000l;
		System.out.println(elapsedTime == 1 ? elapsedTime + " second" : elapsedTime + " seconds");
	}

	// Existance from CORRECT
	@Test
	public void testInternetConnection() {
		try {
			final URL url = new URL("http://www.google.com");
			final URLConnection conn = url.openConnection();
			conn.connect();
			conn.getInputStream().close();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			fail("Internet connection is not available.");
		}
	}
}
