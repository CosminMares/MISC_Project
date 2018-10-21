/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sas.misc.structs;

import java.io.Serializable;

/**
 *
 * @author MCL
 */
public class PrivateMessage extends Message implements Serializable {

    private String destinatar;

    public PrivateMessage(String dest, String expeditor, String continut) {
        super(expeditor, continut);
        destinatar = dest;
    }

    public String toString() {
        return "(priv)" + super.toString();
    }

    public String getRecipient() {
        return destinatar;
    }
}
