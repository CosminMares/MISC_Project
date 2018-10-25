package sas.misc.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sas.misc.structs.PrivateMessage;

public class PrivateMessageTest {

	private PrivateMessage pm;
	private String expeditor;
	private String continut;
	private String destinatar;
	String stringLength20 = "amcdloedlcfrodscvghy";
	String stringLengthUp20 = "amcdloedlcfrodscvghyp";
	String stringLength1100 = get1100Characters(stringLength20);
	String stringLengthUp1100 = get1100Characters(stringLength20) + 'c';


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
		assertEquals(expected,pm.getExpeditor());
	}
	
	@Test
	public void constructorTestContinut() {
		String expected = "Hello!";
		assertEquals(expected,pm.getContinut());
	}
	
	public String get1100Characters(String seed) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 55; i++) {
			sb.append(seed);
		}
		return sb.toString();
	}


}
