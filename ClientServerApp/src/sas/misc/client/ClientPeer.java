/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sas.misc.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import sas.misc.server.exceptions.InvalidContentLengthException;
import sas.misc.structs.Message;
import sas.misc.structs.PrivateMessage;

/**
 *
 * @author MCL
 */
public class ClientPeer {
	
	//userName trebuie sa fie mai mic de 20 de caractere si mai mare de 0
	//message trebuie sa fie mai mic sau egal cu 1100 de caractere si mai mare ca 0
    private final String userName;
    private final Socket socket;

    public ClientPeer(String name, Socket s) {
        if (name.length() >= 20 || name.length() < 1) throw new InvalidContentLengthException();
    	userName = name;
        socket = s;
    }
    
    public String getUserName() {
    	return this.userName;
    }
    
    public Socket getSocket() {
    	return this.socket;
    }

    public void sendMessage(String message) throws Exception{
        if (message.length() > 1100) throw new InvalidContentLengthException();  	
    	try {
            Message m = new Message(userName, message);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(m);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMessage(String message, String recipient) throws Exception{
    	if (message.length() > 1100 || message.length() < 1) throw new InvalidContentLengthException();
    	if (recipient.length() >= 20 || recipient.length() < 1) throw new InvalidContentLengthException();
    	try {
            PrivateMessage pm = new PrivateMessage(recipient, userName, message);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(pm);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
