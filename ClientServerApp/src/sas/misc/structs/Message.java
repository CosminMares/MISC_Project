/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sas.misc.structs;

import java.io.Serializable;

import sas.misc.server.exceptions.InvalidContentLengthException;
import sas.misc.server.exceptions.InvalidExpeditorLengthException;

/**
 *
 * @author MCL
 */
public class Message implements Serializable {

	// expeditor trebuie sa fie mai mic de 20 de caractere si mai mare de 0
	// continut trebuie sa fie mai mic de 1100 de caractere si mai mare de 0
	private String expeditor;
	private String continut;

	public Message() {

	}

	public Message(String exp, String cont) throws Exception {
		if (!expeditorLength(exp))
			throw new InvalidExpeditorLengthException();

		if (!continutLength(cont))
			throw new InvalidContentLengthException();

		expeditor = exp;
		continut = cont;
	}

	public String getExpeditor() {
		return this.expeditor;
	}

	public String getContinut() {
		return this.continut;
	}

	public void setExpeditor(String expeditor) throws Exception {
		if (!expeditorLength(expeditor))
			throw new InvalidExpeditorLengthException();
		this.expeditor = expeditor;
	}

	public void setContinut(String continut) throws Exception{
		if (!continutLength(continut))
			throw new InvalidContentLengthException();
		this.continut = continut;
	}

	public String toString() {
		return expeditor + ": " + continut;
	}

	public boolean expeditorLength(String expeditor) {
		if (expeditor.length() > 0 && expeditor.length() <= 20)
			return true;
		else
			return false;
	}

	public boolean continutLength(String continut) {
		if (continut.length() > 0 && continut.length() <= 1100)
			return true;
		else
			return false;
	}
}
