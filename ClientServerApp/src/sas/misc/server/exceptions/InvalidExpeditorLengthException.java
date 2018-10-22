package sas.misc.server.exceptions;

public class InvalidExpeditorLengthException extends Exception {
	public InvalidExpeditorLengthException(){
		super();
		System.out.println("Lungimea in caractere a expeditorului nu este potrivita.");
	}
}
