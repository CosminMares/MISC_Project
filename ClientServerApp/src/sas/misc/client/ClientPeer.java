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
import sas.misc.structs.Message;
import sas.misc.structs.PrivateMessage;

/**
 *
 * @author MCL
 */
public class ClientPeer {

    private final String userName;
    private final Socket socket;

    public ClientPeer(String name, Socket s) {
        userName = name;
        socket = s;
    }

    public void sendMessage(String message) throws Exception{
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
