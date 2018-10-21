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
public class Message implements Serializable {

    private String expeditor;
    private String continut;

    public Message(String exp, String cont) {
        expeditor = exp;
        continut = cont;
    }

    public String toString() {
        return expeditor + ": " + continut;
    }
}
