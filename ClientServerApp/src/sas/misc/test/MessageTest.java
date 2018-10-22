package sas.misc.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sas.misc.server.exceptions.InvalidContentLengthException;
import sas.misc.server.exceptions.InvalidExpeditorLengthException;
import sas.misc.structs.Message;

public class MessageTest {

	Message message;
	String stringLength20 = "amcdloedlcfrodscvghy";
	String stringLengthUp20 = "amcdloedlcfrodscvghyp";
	String stringLength1100 = get1100Characters(stringLength20);
	String stringLengthUp1100 = get1100Characters(stringLength20) + 'c';

	@Before
	public void setUp() throws Exception {
		String expeditor = "John";
		String continut = "Hello!";
		message = new Message(expeditor, continut);
	}

	@Test
	public void constructorTestNotNull() {
		assertNotNull(message);
	}

	@Test
	public void constructorExpeditor() {
		String expected = "John";
		assertEquals(expected, message.getExpeditor());
	}

	@Test
	public void constructorContinut() {
		String expected = "Hello!";
		assertEquals(expected, message.getContinut());
	}

	@Test
	public void ExceptionConstructorVerifyExpeditor() throws Exception {
		try {
			Message message = new Message(stringLengthUp20, "Hello!");
			fail("Expected an InvalidExpeditorLengthException to be thrown");
		} catch (InvalidExpeditorLengthException e) {
		}
	}

	@Test
	public void ExceptionConstructorVerifyContinut() throws Exception {
		try {
			Message message = new Message("John", stringLengthUp1100);
			fail("Expected an InvalidContentLengthException to be thrown");
		} catch (InvalidContentLengthException e) {
		}
	}
	
	@Test
	public void testBottomBoundaryExpeditorLength() throws Exception{
		Message m = new Message("A","Hello!");
		if (m.getExpeditor().length() == 1) assert(true);
	}
	
	@Test
	public void testTopBoundaryExpeditorLength() throws Exception{
		Message m = new Message(stringLength20,"Hello!");
		if (m.getExpeditor().length() == 20) assert(true);
	}
	
	@Test
	public void testConstructorPerformance() throws Exception{
		 long startTime = System.currentTimeMillis();

		 Message m = new Message("John", "Hello!");

	     long stopTime = System.currentTimeMillis();
	     long elapsedTime = stopTime - startTime;
	     System.out.println(elapsedTime);
	}
	
	@Test
	public void testToString() {
		String expected = "John: Hello!";
		assertEquals(expected,message.toString());
	}
	
	@Test
	public void testToStringPerformance() throws Exception{
		 long startTime = System.currentTimeMillis();

		 String concat = message.toString();

	     long stopTime = System.currentTimeMillis();
	     long elapsedTime = stopTime - startTime;
	     System.out.println(elapsedTime);
	}
	
	@Test
	public void testSetExpeditor() throws Exception{
		String expected = "John";
		Message m = new Message();
		m.setExpeditor(expected);
		assertEquals(expected, m.getExpeditor());
	}
	
	@Test
	public void testSetExpeditorException()throws Exception {
		try {
			Message message = new Message(stringLengthUp20, "Hello!");
			fail("Expected an InvalidExpeditorLengthException to be thrown");
		} catch (InvalidExpeditorLengthException e) {
		}
	}
	
	@Test
	public void testSetContinut() throws Exception{
		String expected = "Hello!";
		Message m = new Message();
		m.setContinut(expected);
		assertEquals(expected, m.getContinut());
	}
	
	@Test
	public void testSetContinutException() throws Exception {
		try {
			Message message = new Message("John", stringLengthUp1100);
			fail("Expected an InvalidContentLengthException to be thrown");
		} catch (InvalidContentLengthException e) {
		}
	}
	
	@Test
	public void testCrossCheckExpeditor() throws Exception {
		Message m1 = new Message();
		Message m2 = new Message("John","Hello!");
		m1.setExpeditor("John");
		assertEquals(m1.getExpeditor(),m2.getExpeditor());
	}
	
	@Test
	public void testCrossCheckContinut() throws Exception {
		Message m1 = new Message();
		Message m2 = new Message("John","Hello!");
		m1.setContinut("Hello!");
		assertEquals(m1.getContinut(),m2.getContinut());
	}

	public String get1100Characters(String seed) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 55; i++) {
			sb.append(seed);
		}
		return sb.toString();
	}

}
