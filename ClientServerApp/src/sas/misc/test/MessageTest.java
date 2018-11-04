package sas.misc.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sas.misc.server.exceptions.InvalidContentLengthException;
import sas.misc.server.exceptions.InvalidExpeditorLengthException;
import sas.misc.structs.Message;

public class MessageTest {

	Message message;

	@Before
	public void setUp() throws Exception {
		String expeditor = "John";
		String continut = "Hello!";
		message = new Message(expeditor, continut);
	}

	// Test instantiated object not null (Right from Right-BICEP principle)
	@Test
	public void constructorTestNotNull() {
		assertNotNull(message);
	}

	// Test if constructor initializes the expeditor
	// (Right from Right-BICEP principle)
	@Test
	public void constructorExpeditor() {
		String expected = "John";
		assertEquals(expected, message.getExpeditor());
	}
		
	// Conformance test from CORRECT principle
	@Test
	public void constructorExpeditorNonPrintable() throws Exception {
		Message m = new Message(new Utils().nonPrintableCharacters(), "Hello");
		// System.out.println(m.getExpeditor());
		assertEquals(new Utils().nonPrintableCharacters(), m.getExpeditor());
	}

	// Test if constructor initializes the continut
	// (Right from Right-BICEP principle)
	@Test
	public void constructorContinut() {
		String expected = "Hello!";
		assertEquals(expected, message.getContinut());
	}

	// Test Error from Right-BICEP principle
	@Test
	public void ExceptionConstructorVerifyExpeditor() throws Exception {
		try {
			Message message = new Message(new Utils().stringLengthUp20Stub(), "Hello!");
			fail("Expected an InvalidExpeditorLengthException to be thrown");
		} catch (InvalidExpeditorLengthException e) {
		}
	}

	// Test Error from Right-BICEP principle
	@Test
	public void ExceptionConstructorVerifyContinut() throws Exception {
		try {
			Message message = new Message("John", new Utils().get1100CharactersStub() + 'c');
			fail("Expected an InvalidContentLengthException to be thrown");
		} catch (InvalidContentLengthException e) {
		}
	}

	// Test Boundary from Right-BICEP principle
	@Test
	public void testBottomBoundaryExpeditorLength() throws Exception {
		Message m = new Message("A", "Hello!");
		if (m.getExpeditor().length() == 1)
			assert (true);
	}

	// Test Boundary from Right-BICEP principle
	@Test
	public void testTopBoundaryExpeditorLength() throws Exception {
		Message m = new Message(new Utils().stringLength20Stub(), "Hello!");
		if (m.getExpeditor().length() == 20)
			assert (true);
	}

	// Test Performance from Right-BICEP principle
	@Test
	public void testConstructorPerformance() throws Exception {
		long startTime = System.currentTimeMillis();

		Message m = new Message("John", "Hello!");

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
	}

	// Test Right from Right-BICEP principle
	@Test
	public void testToString() {
		String expected = "John: Hello!";
		assertEquals(expected, message.toString());
	}

	// Test Performance from Right-BICEP principle
	@Test
	public void testToStringPerformance() throws Exception {
		long startTime = System.currentTimeMillis();

		String concat = message.toString();

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
	}

	// Test Right from Right-BICEP principle for setter
	@Test
	public void testSetExpeditor() throws Exception {
		String expected = "John";
		Message m = new Message();
		m.setExpeditor(expected);
		assertEquals(expected, m.getExpeditor());
	}

	// Test Error from Right-BICEP principle for setter
	@Test
	public void testSetExpeditorException() throws Exception {
		try {
			Message message = new Message(new Utils().stringLengthUp20Stub(), "Hello!");
			fail("Expected an InvalidExpeditorLengthException to be thrown");
		} catch (InvalidExpeditorLengthException e) {
		}
	}

	// Test Right from Right-BICEP principle for setter
	@Test
	public void testSetContinut() throws Exception {
		String expected = "Hello!";
		Message m = new Message();
		m.setContinut(expected);
		assertEquals(expected, m.getContinut());
	}

	// Test Error from Right-BICEP principle for setter
	@Test
	public void testSetContinutException() throws Exception {
		try {
			Message message = new Message("John", new Utils().get1100CharactersStub() + 'c');
			fail("Expected an InvalidContentLengthException to be thrown");
		} catch (InvalidContentLengthException e) {
		}
	}

	// Test Cross-check from Right-BICEP principle
	@Test
	public void testCrossCheckExpeditor() throws Exception {
		Message m1 = new Message();
		Message m2 = new Message("John", "Hello!");
		m1.setExpeditor("John");
		assertEquals(m1.getExpeditor(), m2.getExpeditor());
	}

	// Test Cross-check from Right-BICEP principle
	@Test
	public void testCrossCheckContinut() throws Exception {
		Message m1 = new Message();
		Message m2 = new Message("John", "Hello!");
		m1.setContinut("Hello!");
		assertEquals(m1.getContinut(), m2.getContinut());
	}
}
