/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otros;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class ApacheCommons {

    String servidor = "ftp.fondolagranja.org";
    String usuario = "u231868887.darwinomar";
    String password = "omarguevara47158520";
    String ruta = "/ArchivosQR/";

    public void cargarr() {

        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(servidor);
            ftpClient.login(usuario, password);

            //Verificar conexión con el servidor.
            int reply = ftpClient.getReplyCode();

            System.out.println("Respuesta recibida de conexión FTP:" + reply);

            if (FTPReply.isPositiveCompletion(reply)) {
                System.out.println("Conectado Satisfactoriamente");
            } else {
                System.out.println("Imposible conectarse al servidor");
            }

            //Verificar si se cambia de direcotirio de trabajo
            ftpClient.changeWorkingDirectory("/ArqhivosQR/");//Cambiar directorio de trabajo
            System.out.println("Se cambió satisfactoriamente el directorio");
            //Activar que se envie cualquier tipo de archivo

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//            ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
            BufferedInputStream buffIn = null;
            buffIn = new BufferedInputStream(new FileInputStream("E://ccc.pdf"));//Ruta del archivo para enviar
            ftpClient.enterLocalPassiveMode();
            ftpClient.storeFile("/", buffIn);//Ruta completa de alojamiento en el FTP
            ftpClient.completePendingCommand();
            buffIn.close(); //Cerrar envio de arcivos al FTP
            ftpClient.logout(); //Cerrar sesión
            ftpClient.disconnect();//Desconectarse del servidor
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Algo malo sucedió");
        }

    }

}
