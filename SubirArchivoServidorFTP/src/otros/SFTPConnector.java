/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otros;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.IOException;

/**
 * Clase encargada de establecer conexi&oacute;n y ejecutar comandos SFTP.
 *
 * @author mmartinez
 */
public class SFTPConnector {

    /**
     * Sesi&oacute;n SFTP establecida.
     */
    private Session session;

    /**
     * Establece una conexi&oacute;n SFTP.
     *
     * @param username Nombre de usuario.
     * @param password Contrase&ntilde;a.
     * @param host Host a conectar.
     * @param port Puerto del Host.
     *
     * @throws JSchException Cualquier error al establecer conexi&oacute;n SFTP.
     * @throws IllegalAccessException Indica que ya existe una conexi&oacute;n
     * SFTP establecida.
     */
    public void connect(String username, String password, String host, int port)
            throws JSchException, IllegalAccessException {
        if (this.session == null || !this.session.isConnected()) {
            JSch jsch = new JSch();

            this.session = jsch.getSession(username, host, port);
            this.session.setPassword(password);

            // Parametro para no validar key de conexion.
//            this.session.setConfig("StrictHostKeyChecking", "no");

            this.session.connect();
        } else {
            throw new IllegalAccessException("Sesion SFTP ya iniciada.");
        }
    }

    /**
     * A&ntilde;ade un archivo al directorio FTP usando el protocolo SFTP.
     *
     * @param ftpPath Path del FTP donde se agregar&aacute; el archivo.
     * @param filePath Directorio donde se encuentra el archivo a subir en
     * disco.
     * @param fileName Nombre que tendra el archivo en el destino.
     *
     * @throws IllegalAccessException Excepci&oacute;n lanzada cuando no hay
     * conexi&oacute;n establecida.
     * @throws JSchException Excepci&oacute;n lanzada por alg&uacute;n error en
     * la ejecuci&oacute;n del comando SFTP.
     * @throws SftpException Error al utilizar comandos SFTP.
     * @throws IOException Excepci&oacute;n al leer el texto arrojado luego de
     * la ejecuci&oacute;n del comando SFTP.
     */
    public final void addFile(String ftpPath, String filePath,
            String fileName) throws IllegalAccessException, IOException,
            SftpException, JSchException {
        if (this.session != null && this.session.isConnected()) {

            // Abrimos un canal SFTP. Es como abrir una consola.
            ChannelSftp channelSftp = (ChannelSftp) this.session.
                    openChannel("sftp");

            // Nos ubicamos en el directorio del FTP.
            channelSftp.cd(ftpPath);
            channelSftp.connect();

            System.out.println(String.format("Creando archivo %s en el "
                    + "directorio %s", fileName, ftpPath));
            channelSftp.put(filePath, fileName);

            System.out.println("Archivo subido exitosamente");

            channelSftp.exit();
            channelSftp.disconnect();
        } else {
            throw new IllegalAccessException("No existe sesion SFTP iniciada.");
        }
    }

    /**
     * Cierra la sesi&oacute;n SFTP.
     */
    public final void disconnect() {
        this.session.disconnect();
    }
}
