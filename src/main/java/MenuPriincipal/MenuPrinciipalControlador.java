package MenuPriincipal;

import java.awt.Color;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class MenuPrinciipalControlador {

    public void cargarTextoATxtArea(JTextArea textArea, File archivo) {
        textArea.setText("");//borramos el texto del textArea en caso lo haya
        String linea;
        FileReader fr;
        try {
            fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            while ((linea = br.readLine()) != null) {
                textArea.append(linea + "\n");
            }
        } catch (IOException ex) {
        }
    }

    public void contarFilasYColumnas(JLabel labelFila, JLabel labelColumna, javax.swing.event.CaretEvent evt) {
        JTextArea textoSeleccionado = (JTextArea) evt.getSource();//obtener una referencia al JTextArea en el que se ha producido el evento
        int linea;//valores iniciales
        int columna;//
        try {
            int posicionDelCursor = textoSeleccionado.getCaretPosition();// da la posición del cursor relativa al número de caracteres insertados
            linea = textoSeleccionado.getLineOfOffset(posicionDelCursor);//devuelve el numero de linea en donde esta el cursor
            columna = posicionDelCursor - textoSeleccionado.getLineStartOffset(linea);//posicion del cursor menos el número de caracteres que hay en las anteriores líneas   
            linea += 1;// Ya que las líneas las cuenta desde la 0
            labelFila.setText(String.valueOf(linea));//cargamos los valores a las labels
            labelColumna.setText(String.valueOf(columna));//
        } catch (BadLocationException ex) {
        }
    }

    public void buscarArchivo(JTextArea textArea) {
        JFileChooser chooser = new JFileChooser();//creamos el nuevo objeto filechoooser
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos .txt", "txt");
        chooser.setFileFilter(filter);//le damos el filtro al chooser
        chooser.setDialogTitle("Seleccionar archivo");//le damos un titulo al dialog
        chooser.setAcceptAllFileFilterUsed(false);//eliminamos la opcion "todos los archivos" del filechooser
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {//esperamos a que se de el una opcion valida
            File fichero = chooser.getSelectedFile();//decimos que el fichero sera igual al archivo elegido
            cargarTextoATxtArea(textArea, fichero);
        }
    }

    public void buscarpalabra(JTextArea textArea, String palabraABuscar) {

        DefaultHighlighter.DefaultHighlightPainter resaltadorVerde = new DefaultHighlightPainter(Color.GREEN);//este pintara el texto de color verde
        Highlighter marcaTextos = textArea.getHighlighter();//esto tiene el metodo para indicar
        marcaTextos.removeAllHighlights();//borramos todos los resaltes

        String contenido = textArea.getText();//contenido del textarea
        int inicioDePalabra = 0;
        String palabraAComparar = "";
        for (int x = 0; x < contenido.length(); x++) {//este for explora hasta la ultima letra de la cadena
            if (contenido.charAt(x) == ' ' || contenido.charAt(x) == '\n') {//si es espacio o enter entonces hasta aqui se forma una palabra                                                                          
                //comparamos la palabra construida y la que se quiere buscar
                if (palabraAComparar.equals(palabraABuscar)) {
                    try {
                        //si son iguales entonces usamos el inicio de la palabra y y la x para marcar el texto en esa posicion
                        marcaTextos.addHighlight(inicioDePalabra, x, resaltadorVerde);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }                 
                }
                palabraAComparar = "";//reiniciamos la paralbra referencia
                inicioDePalabra = x + 1;//el inicio de otra palabra sera la pocision despues de un enter o espacio
            } else {//si no hay espacio o enter entonces sumamos la letra a la palabra
                palabraAComparar = palabraAComparar + contenido.charAt(x);
                if (x == contenido.length() - 1) {//si estamos al final de la cadena
                    if (palabraAComparar.equals(palabraABuscar)) {//y la palabra es igual a la a buscar entonces la marcamos
                        try {
                            marcaTextos.addHighlight(inicioDePalabra, (x + 1), resaltadorVerde);
                        } catch (BadLocationException ex) {
                            ex.printStackTrace();
                        }                 
                    }
                }
            }
        }
    }
}
