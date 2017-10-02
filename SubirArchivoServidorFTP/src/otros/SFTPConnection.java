/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otros;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.IOException;

/**
 *
 * @author mmartinez
 */
public class SFTPConnection {

    private static final String USERNAME = "maframaran";
    private static final String HOST = "localhost";
    private static final int PORT = 22;
    private static final String PASSWORD = "maframaran01";

    public static void main(String[] args) {

        try {
            SFTPConnector sshConnector = new SFTPConnector();

            sshConnector.connect(USERNAME, PASSWORD, HOST, PORT);
            sshConnector.addFile("/home/MyFiles", "C:/MyFiles/archivo.txt",
                    "archivo.txt");
            sshConnector.disconnect();
        } catch (JSchException ex) {
            ex.printStackTrace();

            System.out.println(ex.getMessage());
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();

            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();

            System.out.println(ex.getMessage());
        } catch (SftpException ex) {
            ex.printStackTrace();

            System.out.println(ex.getMessage());
        }
    }
}
