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
public class PrivateMessage extends Message implements Serializable {
	//destinatar trebuie sa aiba intre 1 si maximun 20 de caractere
    private String destinatar;

    public PrivateMessage(String dest, String expeditor, String continut) throws Exception {
    	super(expeditor, continut);
    	if (dest.length() < 1 || dest.length() > 20) throw new InvalidContentLengthException();
        destinatar = dest;
    }

    public String toString() {
        return "(priv)" + super.toString();
    }

    public String getRecipient() {
        return destinatar;
    }
}
