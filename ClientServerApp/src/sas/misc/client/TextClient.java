/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sas.misc.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MCL
 */
public class TextClient {

	public static void main(String args[]) {
		String name;
		Socket sck = null;
		ClientPeer clientPeer = null;
		String numedest;
		StringBuilder sb = new StringBuilder();

		// citire nume utilizator
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduceti-va numele ");
		name = sc.next();

		try {
			// creare de soket pe localHost
			sck = new Socket("127.0.0.1", 9000);
			System.out.println("socket-ul a fost creat!");
		} catch (IOException ex) {
			Logger.getLogger(TextClient.class.getName()).log(Level.SEVERE, null, ex);
		}

		// instantiere obiect de tip ClientPeer
		try {
			clientPeer = new ClientPeer(name, sck);
			System.out.println("obj ClientPeer a fost creat!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (sck != null) {
			while (sck.isConnected()) {
				String text = null;
				try {
					System.out.println("introduceti mesajul: ");
					BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
					text = in.readLine();
				} catch (IOException ex) {
					Logger.getLogger(TextClient.class.getName()).log(Level.SEVERE, null, ex);
				}
				if (text.equals("/q")) {
					try {
						sck.close();
					} catch (IOException ex) {
						Logger.getLogger(TextClient.class.getName()).log(Level.SEVERE, null, ex);
					}
				} else if (text.contains("/w")) {
					text = text.replaceFirst("/w ", "");
					numedest = recipientName(text);
					sb.append(numedest);
					sb.append(' ');
					String x = sb.toString();
					text = text.replaceFirst(x, "");
					try {
						clientPeer.sendMessage(text, numedest);
					} catch (Exception e) {
						Logger.getLogger(TextClient.class.getName()).log(Level.SEVERE, null, e);
					}
				} else {
					try {
						clientPeer.sendMessage(text);
					} catch (Exception e) {
						Logger.getLogger(TextClient.class.getName()).log(Level.SEVERE, null, e);
					}
				}
				numedest = null;
			}
		}
		System.out.println("mesaj trimis!");
		try {
			sck.close();
		} catch (IOException ex) {
			Logger.getLogger(TextClient.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static String recipientName(String input) {
		String result = "";
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == ' ') {
				result = input.substring(0, i);
				break;
			}
		}
		return result;
	}
}
