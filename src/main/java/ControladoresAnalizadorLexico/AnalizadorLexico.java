package ControladoresAnalizadorLexico;

import ControladoresDeReportes.Reporte;
import java.util.ArrayList;
import javax.swing.JTable;

public class AnalizadorLexico {

    private JTable tablaErrores;
    private JTable tablaTokens;
    private JTable tablaRecuento;

    private ArrayList<Error> errores = new ArrayList<>();
    private ArrayList<Token> tokens = new ArrayList<>();
    private ArrayList<String> trancisionesDelAutomata = new ArrayList<>();

    /**
     * Contrusctor inicializa las tablas que serviran para hacer los reportes
     *
     * @param tablaErrores
     * @param tablaTokens
     * @param tablaRecuento
     */
    public AnalizadorLexico(JTable tablaErrores, JTable tablaTokens, JTable tablaRecuento) {
        this.tablaErrores = tablaErrores;
        this.tablaTokens = tablaTokens;
        this.tablaRecuento = tablaRecuento;
    }

    public ArrayList<String> comenzarAnalisis(String textoAAnalizar) {
        //estos contadores serviran en caso haya error, se iran sumando
        int fila = 1;
        int columna = 0;
        //este String guardara la palabra que mandaremos a identificar
        String palabraAAnalizar = "";
        for (int x = 0; x < textoAAnalizar.length(); x++) {//for analiza todo el contenido del texto enviado
            if (x == (textoAAnalizar.length() - 1)) {//vamios si se trata del final de la cadena
                palabraAAnalizar = palabraAAnalizar + textoAAnalizar.charAt(x);//obtenemos la letra final
                columna++;
            }
            if (textoAAnalizar.charAt(x) == '\n' || textoAAnalizar.charAt(x) == ' ' || x == (textoAAnalizar.length() - 1)) {//si se detecta un espacio entonces hasta aqui hay una palabra formada
                if (!palabraAAnalizar.equals("")) {//analizaremos la paabla siempre y cuedo no este vacia
                    //investigamos de que token se trata
                    if (verSiEsIdentificador(palabraAAnalizar)) {
                        tokens.add(new Token("Identificador", palabraAAnalizar, fila, columna));
                        System.out.println("Identificador");
                    } else if (verSiEsNumero(palabraAAnalizar)) {
                        tokens.add(new Token("Numero", palabraAAnalizar, fila, columna));
                        System.out.println("Numero");
                    } else if (verSiEsDecimal(palabraAAnalizar)) {
                        tokens.add(new Token("Decimal", palabraAAnalizar, fila, columna));
                        System.out.println("Decimal");
                    } else if (verSiEsPuntuacion(palabraAAnalizar)) {
                        tokens.add(new Token("Signo de puntuacion", palabraAAnalizar, fila, columna));
                        System.out.println("signo de puntuacion");
                    } else if (verSiEsOperador(palabraAAnalizar)) {
                        tokens.add(new Token("Operador", palabraAAnalizar, fila, columna));
                        System.out.println("Operador");
                    } else if (verSiEsAgrupacion(palabraAAnalizar)) {
                        tokens.add(new Token("Signo de agrupacion", palabraAAnalizar, fila, columna));
                        System.out.println("Signo de agrupacion");
                    } else {
                        errores.add(new Error(palabraAAnalizar, fila, columna));
                    }

                }
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
        //aqui acabamos de analizar todas las palabras del jtext generamos los reportes
        Reporte reportes = new Reporte(tablaErrores, tablaTokens, tablaRecuento, errores, tokens);
        reportes.cargarTablaErrores();
        reportes.cargarTablaTokens();
        reportes.cargarTablaRecuento();
        return trancisionesDelAutomata;
    }

    public boolean verSiEsNumero(String palabraAAnalizar) {
        ArrayList<String> transiciones = new ArrayList<>();//en este guardaremos todas las transiciones
        transiciones.add("Con el texto " + palabraAAnalizar);//damos cabecera
        String estado = "s1";
        String estadoAux = "";
        char charEnPosicionX;//esta variable almacenara el char que estemos analizando en el for
        for (int x = 0; x < palabraAAnalizar.length(); x++) {//for que exprolara caracter por caracter la palabra
            charEnPosicionX = palabraAAnalizar.charAt(x);//damos valor a la varible char          
            if (verificarSiCaracterPerteneceAAlfabetoDeNumero(charEnPosicionX)) {//verificamos siempre que el char sea un numero del 0 al 9
                if (estado.equals("s1")) { //si estamos en el estado s1 y se trata de un numero etonces nos movemos al s4
                    estadoAux = estado;
                    estado = "s4";
                    //agregamos la transicion
                    transiciones.add("Con el caracter " + palabraAAnalizar.charAt(x) + " me movi del estado " + estadoAux + " al estado " + estado);
                } else if (estado.equals("s4")) {//si estamos en el s4 y se trata de un numero entonces nos movemos al s4  
                    estado = "s4";
                    transiciones.add("Con el caracter " + palabraAAnalizar.charAt(x) + " me movi del estado " + estadoAux + " al estado " + estado);
                }
            } else {//si no se trata de un caracter valido entonces invalidamos el estado y rompemos el for
                estado = "no pertenece al alfabeto";
                break;
            }

        }
        if (estado.equals("s4")) {
            transiciones.add("Me encuentro en mi estado de aceptacion s4. El texto: " + palabraAAnalizar + " es un numero");
            trancisionesDelAutomata.addAll(0, transiciones);//le pasamos todas las transiciones generadas
            return true;
        } else {
            return false;
        }
    }

    public boolean verSiEsIdentificador(String palabraAAnalizar) {
        ArrayList<String> transiciones = new ArrayList<>();//en este guardaremos todas las transiciones
        transiciones.add("Con el texto " + palabraAAnalizar);//damos cabecera
        String estado = "s1";
        String estadoAux = "";
        //vemos que el primer char sea una letra
        if ((palabraAAnalizar.charAt(0) >= 65 && palabraAAnalizar.charAt(0) <= 90) || (palabraAAnalizar.charAt(0) >= 97 && palabraAAnalizar.charAt(0) <= 122)) {
            //no hacemos nada, solo verificamos el valor de verdad
        } else {
            return false;//si no esntonces no es un identificador
        }//si llega aqui entonces se cumplio el if y seguimos analizando
        for (int x = 0; x < palabraAAnalizar.length(); x++) {
            //verificamos que el caracter sea parte del alfaberto del automata
            if (verificarSiCaracterPerteneceAAlfabetoDeIdentificador(palabraAAnalizar.charAt(x))) {
                estadoAux = estado;
                estado = "s3";//si entra entonces pasamos al estadp de acptacion
                transiciones.add("Con el caracter " + palabraAAnalizar.charAt(x) + " me movi del estado " + estadoAux + " al estado " + estado);
            } else {
                return false;
            }
        }
        //una vez acabado el for entonces se encuentra en el s3 y es un identificador
        transiciones.add("Me encuentro en el estado de aceptacion s3, el texto: " + palabraAAnalizar + " es un identificador");
        trancisionesDelAutomata.addAll(0, transiciones);//le pasamos todas las transiciones generadas
        return true;
    }

    public boolean verSiEsDecimal(String palabraAAnalizar) {
        ArrayList<String> transiciones = new ArrayList<>();//en este guardaremos todas las transiciones
        transiciones.add("Con el texto " + palabraAAnalizar);//damos cabecera
        String estado = "s1";
        String estadoAux = "";
        int contadorDePuntoDecimal = 0;
        char charEnPosicionX;//esta variable almacenara el char que estemos analizando en el for
        //primera validacion debera ser si se trata de un numero en la primera posicion
        if (palabraAAnalizar.charAt(0) >= 48 && palabraAAnalizar.charAt(0) <= 57) {
            //
        } else {
            return false;
        }
        for (int x = 0; x < palabraAAnalizar.length(); x++) {
            charEnPosicionX = palabraAAnalizar.charAt(x);//guardamos el cahr a analizar en la variable
            if (verificarSiCaracterPrteneceAAlfabetoDeDecimal(charEnPosicionX)) {//analizamos si el caracter pertenece al alfabeto
                //si estamos en el estado s1 y se trata de un numero etonces nos movemos al s4
                if (estado.equals("s1") && (charEnPosicionX >= 48 && charEnPosicionX <= 57)) {
                    estadoAux = estado;
                    estado = "s4";
                    transiciones.add("Con el caracter " + palabraAAnalizar.charAt(x) + " me movi del estado " + estadoAux + " al estado " + estado);
                    //si estamos en el estado s4 y se trata de un numero etonces nos quedamos en el s4
                } else if (estado.equals("s4") && (charEnPosicionX >= 48 && charEnPosicionX <= 57)) {
                    estado = "s4";
                    transiciones.add("Con el caracter " + palabraAAnalizar.charAt(x) + " me movi del estado " + estadoAux + " al estado " + estado);
                    //si estamos en el s4 y se trata de un punto entonces nos movemos al s5
                } else if (estado.equals("s4") && (charEnPosicionX == '.')) {
                    estadoAux = estado;
                    estado = "s5";
                    transiciones.add("Con el caracter " + palabraAAnalizar.charAt(x) + " me movi del estado " + estadoAux + " al estado " + estado);
                    //si estamos en el estado s5 y se trata de un numero etonces nos movemos al s6    
                } else if (estado.equals("s5") && (charEnPosicionX >= 48 && charEnPosicionX <= 57)) {
                    estadoAux = estado;
                    estado = "s6";
                    transiciones.add("Con el caracter " + palabraAAnalizar.charAt(x) + " me movi del estado " + estadoAux + " al estado " + estado);
                    //si estamos en el estado s6 y se trata de un numero etonces nos quedamos en el s6
                } else if (estado.equals("s6") && (charEnPosicionX >= 48 && charEnPosicionX <= 57)) {
                    estado = "s6";
                    transiciones.add("Con el caracter " + palabraAAnalizar.charAt(x) + " me movi del estado " + estadoAux + " al estado " + estado);
                }
                if (charEnPosicionX == '.') {
                    contadorDePuntoDecimal++;
                }
            } else {//si el char no pertenece al alfabeto etonces invalidamos el estado y rompemos el for
                estado = "caher no pertenece al alfabeto";
                break;
            }
        }
        if (contadorDePuntoDecimal == 1) {
            //cuando acabamos de explorar la palabra y el estado es s6 entonces es un decimal de lo contrario no
            if (estado.equals("s6")) {//vemos si el estado es el estado de aceptacion
                transiciones.add("Me encuentro en mi estado de aceptacion s6. El texto: " + palabraAAnalizar + " es un Decimal");
                trancisionesDelAutomata.addAll(0, transiciones);//le pasamos todas las transiciones generadas
                return true;//si entra entonces devolvemos true
            } else {//si no entonces devolvemos false
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean verSiEsPuntuacion(String palabraAAnalizar) {
        ArrayList<String> transiciones = new ArrayList<>();//en este guardaremos todas las transiciones
        transiciones.add("Con el texto " + palabraAAnalizar);//damos cabecera
        String estado = "s1";
        String estadoAux = "";
        char charEnPosicionX;//esta variable almacenara el char que estemos analizando en el for
        int contadorDeChars = 0;
        for (int x = 0; x < palabraAAnalizar.length(); x++) {//
            contadorDeChars++;//este contador sirve para verificar que la palabra solo tenga 1 caracter
            charEnPosicionX = palabraAAnalizar.charAt(x);//guardamos el cahr a analizar en la variable
            if (verificarSiCaracterPrteneceAAlfabetoDePuntuacion(charEnPosicionX)) {
                estadoAux = estado;
                estado = "s7";
                transiciones.add("Con el caracter " + palabraAAnalizar.charAt(x) + " me movi del estado " + estadoAux + " al estado " + estado);
            }
        }
        if (contadorDeChars > 1) {//vamos que la palabra solo tenga 1 char
            return false;
        }
        if (estado.equals("s7")) {//vemos si el estado es el estado de aceptacion
            transiciones.add("Me encuentro en mi estado de aceptacion s7. El texto: " + palabraAAnalizar + " es un Signo de puntuacion");
            trancisionesDelAutomata.addAll(0, transiciones);//le pasamos todas las transiciones generadas
            return true;//si entra entonces devolvemos true
        } else {//si no entonces devolvemos false
            return false;
        }
    }

    public boolean verSiEsOperador(String palabraAAnalizar) {
        ArrayList<String> transiciones = new ArrayList<>();//en este guardaremos todas las transiciones
        transiciones.add("Con el texto " + palabraAAnalizar);//damos cabecera
        String estado = "s1";
        String estadoAux = "";
        char charEnPosicionX;//esta variable almacenara el char que estemos analizando en el for
        int contadorDeChars = 0;
        for (int x = 0; x < palabraAAnalizar.length(); x++) {//
            contadorDeChars++;//este contador sirve para verificar que la palabra solo tenga 1 caracter
            charEnPosicionX = palabraAAnalizar.charAt(x);//guardamos el cahr a analizar en la variable
            if (verificarSiCaracterPrteneceAAlfabetoDeOperador(charEnPosicionX)) {
                estadoAux = estado;
                estado = "s8";
                transiciones.add("Con el caracter " + palabraAAnalizar.charAt(x) + " me movi del estado " + estadoAux + " al estado " + estado);
            }
        }
        if (contadorDeChars > 1) {//vamos que la palabra solo tenga 1 char
            return false;
        }
        if (estado.equals("s8")) {//vemos si el estado es el estado de aceptacion
            transiciones.add("Me encuentro en mi estado de aceptacion s8. El texto: " + palabraAAnalizar + " es un Operador");
            trancisionesDelAutomata.addAll(0, transiciones);//le pasamos todas las transiciones generadas
            return true;//si entra entonces devolvemos true
        } else {//si no entonces devolvemos false
            return false;
        }
    }

    public boolean verSiEsAgrupacion(String palabraAAnalizar) {
        ArrayList<String> transiciones = new ArrayList<>();//en este guardaremos todas las transiciones
        transiciones.add("Con el texto " + palabraAAnalizar);//damos cabecera
        String estado = "s1";
        String estadoAux = "";
        char charEnPosicionX;//esta variable almacenara el char que estemos analizando en el for
        int contadorDeChars = 0;
        for (int x = 0; x < palabraAAnalizar.length(); x++) {//
            contadorDeChars++;//este contador sirve para verificar que la palabra solo tenga 1 caracter
            charEnPosicionX = palabraAAnalizar.charAt(x);//guardamos el cahr a analizar en la variable
            if (verificarSiCaracterPrteneceAAlfabetoDeAgrupacion(charEnPosicionX)) {
                estadoAux = estado;
                estado = "s9";
                transiciones.add("Con el caracter " + palabraAAnalizar.charAt(x) + " me movi del estado " + estadoAux + " al estado " + estado);
            }
        }
        if (contadorDeChars > 1) {//vamos que la palabra solo tenga 1 char
            return false;
        }
        if (estado.equals("s9")) {//vemos si el estado es el estado de aceptacion
            transiciones.add("Me encuentro en mi estado de aceptacion s9. El texto: " + palabraAAnalizar + " es un Signo de agrupacion");
            trancisionesDelAutomata.addAll(0, transiciones);//le pasamos todas las transiciones generadas
            return true;//si entra entonces devolvemos true
        } else {//si no entonces devolvemos false
            return false;
        }
    }

    private boolean verificarSiCaracterPrteneceAAlfabetoDePuntuacion(char caracter) {
        return (caracter == '.' || caracter == ',' || caracter == ';' || caracter == ':');
    }

    private boolean verificarSiCaracterPrteneceAAlfabetoDeOperador(char caracter) {
        return (caracter == '+' || caracter == '-' || caracter == '*' || caracter == '/' || caracter == '%');
    }

    private boolean verificarSiCaracterPrteneceAAlfabetoDeAgrupacion(char caracter) {
        return (caracter == '(' || caracter == ')' || caracter == '[' || caracter == ']' || caracter == '{' || caracter == '}');
    }

    private boolean verificarSiCaracterPrteneceAAlfabetoDeDecimal(char caracter) {
        return ((caracter >= 48 && caracter <= 57) || caracter == '.');
    }

    private boolean verificarSiCaracterPerteneceAAlfabetoDeIdentificador(char caracter) {
        return (caracter >= 65 && caracter <= 90) || (caracter >= 97 && caracter <= 122) || (caracter >= 48 && caracter <= 57);
    }

    private boolean verificarSiCaracterPerteneceAAlfabetoDeNumero(char caracter) {
        return (caracter >= 48 && caracter <= 57);
    }
}
