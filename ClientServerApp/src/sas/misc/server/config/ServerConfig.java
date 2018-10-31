/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sas.misc.server.config;

/**
 *
 * @author MCL
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import sas.misc.server.exceptions.InvalidFormatException;
import sas.misc.server.exceptions.MissingKeyException;
import sas.misc.server.exceptions.UnknownKeyException;

public class ServerConfig {

    private int tcpPort = -1;
    private int maxClients = -1;

    public ServerConfig() throws IOException,
            InvalidFormatException, MissingKeyException, UnknownKeyException {
        readConfigFile("server.conf");
    }

    public ServerConfig(String fileName) throws IOException,
            InvalidFormatException, MissingKeyException, UnknownKeyException {
        readConfigFile(fileName);
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public void readConfigFile(String file) throws IOException,
            InvalidFormatException, MissingKeyException, UnknownKeyException {

        FileInputStream fileInputStream = new FileInputStream(file);
        @SuppressWarnings("resource")
        BufferedReader buffer = new BufferedReader(new InputStreamReader(fileInputStream));
        String line = buffer.readLine();
        while (line != null) {
            while (line.isEmpty()) {
                line = buffer.readLine();
            }
            String trimedLine = line.trim();
            if (trimedLine.startsWith("#") || line.length() == 0) {
            } else {
                if (trimedLine.startsWith("TCP_PORT=")) {
                    tcpPort = Integer.parseInt(((String[]) trimedLine.split("="))[1]);
                }
                if (trimedLine.startsWith("MAX_CLIENTS=")) {
                    maxClients = Integer.parseInt((trimedLine.split("="))[1]);
                }
                if (trimedLine.contains("=") && !trimedLine.startsWith("TCP_PORT=")
                        && !trimedLine.startsWith("MAX_CLIENTS=")) {
                    throw new UnknownKeyException("Unknown Key");
                }
                if (!trimedLine.contains("=")) {
                    throw new InvalidFormatException("Invalid Format");
                }
            }
            line = buffer.readLine();
        }
        buffer.close();
        fileInputStream.close();
        if ((tcpPort == -1) || (maxClients == -1)) {
            throw new MissingKeyException("Missing Key");
        }
    }
}
