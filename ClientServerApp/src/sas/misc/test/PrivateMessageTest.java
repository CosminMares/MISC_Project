package sas.misc.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sas.misc.server.exceptions.InvalidContentLengthException;
import sas.misc.structs.PrivateMessage;

public class PrivateMessageTest {

	private PrivateMessage pm;
	private String expeditor;
	private String continut;
	private String destinatar;

	@Before
	public void setUp() throws Exception {
		this.expeditor = "Bob";
		this.continut = "Hello!";
		this.destinatar = "Alice";
		pm = new PrivateMessage(destinatar, expeditor, continut);
	}

	@Test
	public void constructorTestNotNull() {
		assertNotNull(pm);
	}

	@Test
	public void constructorTestDestinatar() {
		String expected = "Alice";
		assertEquals(expected, pm.getRecipient());
	}

	@Test
	public void constructorTestExpeditor() {
		String expected = "Bob";
		assertEquals(expected, pm.getExpeditor());
	}

	@Test
	public void constructorTestContinut() {
		String expected = "Hello!";
		assertEquals(expected, pm.getContinut());
	}

	// test Error for constructor
	// lungime destinatar mai mica decat 1
	@Test
	public void constructorLowErrorTest() {
		String destinatar = "";
		try {
			PrivateMessage pm = new PrivateMessage(destinatar, expeditor, continut);
			fail("Expected an InvalidContentLengthException to be thrown");
		} catch (Exception e) {
		}
	}

	// test Error for constructor
	// lungime destinatar mai mica decat 20
	@Test
	public void constructorTopErrorTest() throws Exception {
		String destinatar = new Utils().stringLengthUp20;
		try {
			PrivateMessage pm = new PrivateMessage(destinatar, expeditor, continut);
			fail("Expected an InvalidContentLengthException to be thrown");
		} catch (Exception e) {
		}
	}

	// test up boundary for constructor
	// lungime destinatar de 20 de caractere
	@Test
	public void constructorTopBoundaryTest() throws Exception {
		String destinatar = new Utils().stringLength20;
		try {
			PrivateMessage pm = new PrivateMessage(destinatar, expeditor, continut);
		} catch (InvalidContentLengthException e) {
			fail("No exception was expected");
		}
	}

	// test up boundary for constructor
	// lungime destinatar de 1 caracter
	@Test
	public void constructorLowBoundaryTest() throws Exception {
		String destinatar = "1";
		try {
			PrivateMessage pm = new PrivateMessage(destinatar, expeditor, continut);
		} catch (InvalidContentLengthException e) {
			fail("No exception was expected");
		}
	}
}
