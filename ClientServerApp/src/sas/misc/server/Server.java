/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sas.misc.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import sas.misc.server.config.ServerConfig;
import sas.misc.server.exceptions.InvalidFormatException;
import sas.misc.server.exceptions.MissingKeyException;
import sas.misc.server.exceptions.UnknownKeyException;

/**
 *
 * @author MCL
 */
public class Server {

    public static void main(String args[]) {
        Socket s;
        int port = 0;
        try {
            ServerConfig serverConfig = new ServerConfig();
            port = serverConfig.getTcpPort();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MissingKeyException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownKeyException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Waiting for a connection...");
            s = serverSocket.accept();
            System.out.println("Connection established!!!");
            ServerPeer serverPeer = new ServerPeer(s);
            serverSocket.close();
            System.out.println("obj ServerPeer pe socket-ul conectat a fost creat!");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
