/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subirarchivoservidorftp;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author OmarGuevara
 */
public class principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            subirArchivosServidorFTP obj = new subirArchivosServidorFTP();
            obj.conectar("servidor", "usuario", "contraseña");
            obj.uploadFile("E:\\archivo.pdf", "archivo.pdf", "/Ruta_servidor_ftp/");
            obj.disconnect();
        } catch (Exception ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
