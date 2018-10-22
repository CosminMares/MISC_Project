package sas.misc.server.exceptions;

public class InvalidContentLengthException extends Exception {
	public InvalidContentLengthException(){
		super();
		System.out.println("Lungimea in caractere a continutului nu este potrivita.");
	}
}
