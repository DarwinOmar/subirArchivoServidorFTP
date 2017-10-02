package otros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * @author OmarGuevara Este metodo lo utilizo para guardar los archivos de
 * entradas de materiales y ordenes de compras y servicios 
 */
public class ftp {

    /**
     * Sube un archivo a un servidor FTP
     *
     * @param ftpServer Servidor FTP
     * @param user Usuario
     * @param password Contraseña
     * @param location Directorio donde se subira el archivo
     * @param file Archivo que se va a subir
     * @param debug Para Mostrar las respuestas del servidor
     *
     */
    public static void cargarArchivoFTP(String ftpServer, String user, String password, String location, File file, boolean debug) {
        try {
            if (file.exists()) {
                Socket socket = new Socket(ftpServer, 21);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bufferedWriter.write("USER " + user + "\r\n");
                bufferedWriter.flush();
                bufferedWriter.write("PASS " + password + "\r\n");
                bufferedWriter.flush();
                bufferedWriter.write("CWD " + location + "\r\n");
                bufferedWriter.flush();
                bufferedWriter.write("TYPE A\r\n");
                bufferedWriter.flush();
                bufferedWriter.write("PASV\r\n");
                bufferedWriter.flush();
                String response = null;
               
                while ((response = bufferedReader.readLine()) != null) {
                    if (debug) {
                        System.out.println(response);
                    }
                    if (response.startsWith("530")) {
                        System.err.println("Login aunthentication failed");
                        break;
                    }
                    if (response.startsWith("227")) {
                        String address = null;
                        int port = -1;
                        int opening = response.indexOf('(');
                        int closing = response.indexOf(')', opening + 1);
                        if (closing > 0) {
                            String dataLink = response.substring(opening + 1, closing);
                            StringTokenizer tokenizer = new StringTokenizer(dataLink, ",");
                            try {
                                address = tokenizer.nextToken() + "." + tokenizer.nextToken() + "." + tokenizer.nextToken() + "." + tokenizer.nextToken();
                                port = Integer.parseInt(tokenizer.nextToken()) * 256 + Integer.parseInt(tokenizer.nextToken());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                Socket transfer = new Socket(address, port);
                                bufferedWriter.write("STOR " + file.getName() + "\r\n");
                                bufferedWriter.flush();
                                response = bufferedReader.readLine();
                                if (debug) {
                                    System.out.println(response);
                                }
                                if (response.startsWith("150")) {
                                    FileInputStream fileInputStream = new FileInputStream(file);
                                    final int BUFFER_SIZE = 1024;
                                    byte buffer[] = new byte[BUFFER_SIZE];
                                    int len = 0, off = 0;
                                    if (debug) {
                                        System.out.println("uploading file...");
                                    }
                                    while ((len = fileInputStream.read(buffer)) != -1) {
                                        transfer.getOutputStream().write(buffer, off, len);
                                    }
                                    transfer.getOutputStream().flush();
                                    transfer.close();
                                    bufferedWriter.write("QUIT\r\n");
                                    bufferedWriter.flush();
                                    bufferedReader.close();
                                    socket.close();
                                    System.out.println("File sucessfully transferred!");
                                    break;
                                }
                            } catch (Exception e) {
                                System.err.println(e);
                            }
                        }
                    }
                }
            } else {
                System.err.println(file + "no exist!");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
