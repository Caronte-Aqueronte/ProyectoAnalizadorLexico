
package MenuPriincipal;

import java.io.BufferedReader;
import java.io.*;
import javax.swing.JTextArea;

public class MenuPrinciipalControlador {
    public void cargarTextoATxtArea(JTextArea textArea, File archivo){
        String linea;
        FileReader fr;
        try {
            fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            while((linea = br.readLine()) != null){
                textArea.append(linea + "\n");
            }         
        } catch (Exception ex) {
        }   
    }
}
