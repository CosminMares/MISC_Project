/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sas.misc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import sas.misc.structs.Message;

/**
 *
 * @author MCL
 */
public class ServerPeer {

    private Socket socket;

    public ServerPeer(Socket s) {
        try {
            socket = s;
            run();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void run() {
        ObjectInputStream ois = null;
        while (true) {
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                Message m = (Message) ois.readObject();
                System.out.println(m.toString());
            } catch (IOException | ClassNotFoundException ex) {
                //Logger.getLogger(ServerPeer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
