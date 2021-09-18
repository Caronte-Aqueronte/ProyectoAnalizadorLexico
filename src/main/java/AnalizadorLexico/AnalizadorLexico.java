package AnalizadorLexico;

import java.util.ArrayList;

public class AnalizadorLexico {

    private ArrayList<Error> errores = new ArrayList<>();
    private ArrayList<Token> tokens = new ArrayList<>();

    public void comenzarAnalisis(String textoAAnalizar) {
        //estos contadores serviran en caso haya error, se iran sumando
        int fila = 1;
        int columna = 0;
        //este String guardara la palabra que mandaremos a identificar
        String palabraAAnalizar = "";
        for (int x = 0; x < textoAAnalizar.length(); x++) {//for analiza todo el contenido del texto enviado
            if (textoAAnalizar.charAt(x) == '\n' || textoAAnalizar.charAt(x) == ' ' || x == (textoAAnalizar.length() - 1)) {//si se detecta un espacio entonces hasta aqui hay una palabra formada

                if (x == (textoAAnalizar.length() - 1)) {//vamios si se trata del final de la cadena
                    palabraAAnalizar = palabraAAnalizar + textoAAnalizar.charAt(x);//obtenemos la letra final
                    columna++;
                }
                //investigamos de que token se trata
                if (verSiEsIdentificador(palabraAAnalizar)) {
                    System.out.println(palabraAAnalizar);
                    tokens.add(new Token("Identificador", palabraAAnalizar, fila, columna));
                    for (Token item : tokens) {
                        System.out.println(item.getTipoDeToken() + " " + item.getLexema() + " " + item.getFila() + " " + item.getColumna());
                    }
                }// else if () {

                //}
                palabraAAnalizar = "";//despues del cada analizis reseteamos la palabra
            } else {//si no entonces sumamos el char a la palabra
                palabraAAnalizar = palabraAAnalizar + textoAAnalizar.charAt(x);
                columna++;
            }

            if (textoAAnalizar.charAt(x) == '\n') {//si llegamos a detectar un enter entonces sumamos las filas
                fila++;
                columna = 0;
            }
            if (textoAAnalizar.charAt(x) == ' ') {//si llegamos a detectar un espacio entonces sumamos las columnas
                columna++;
            }
        }
    }

    public boolean verSiEsIdentificador(String palabraAAnalizar) {
        String estado = "s1";
        if (!palabraAAnalizar.equals(" ")) {
            //vemos que el primer char sea una letra
            if ((palabraAAnalizar.charAt(0) >= 65 && palabraAAnalizar.charAt(0) <= 90) || (palabraAAnalizar.charAt(0) >= 97 && palabraAAnalizar.charAt(0) <= 122)) {
                estado = "s3";//si entra entonces pasamos al estadp de acptacion
            } else {
                return false;//si no esntonces no es un identificador
            }//si llega aqui entonces se cumplio el if y seguimos analizando
            for (int x = 0; x < palabraAAnalizar.length(); x++) {
                //verificamos que el caracter sea parte del alfaberto del automata
                if (verificarSiCaracterPerteneceAAlfabetoDeIdentificador(palabraAAnalizar.charAt(x))) {
                    estado = "s3";
                } else {
                    return false;
                }
            }//una vez acabado el for entonces se encuentra en el s3 y es un identificador
            return true;
        }
        return false;

    }

    public boolean verificarSiCaracterPerteneceAAlfabetoDeIdentificador(char caracter) {
        if ((caracter >= 65 && caracter <= 90) || (caracter >= 97 && caracter <= 122) || (caracter >= 48 && caracter <= 57)) {
            return true;
        }
        return false;
    }
}
