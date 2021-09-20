package ControladoresDeReportes;

import ControladoresAnalizadorLexico.Error;
import ControladoresAnalizadorLexico.Token;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Reporte {

    private JTable tablaErrores;
    private JTable tablaTokens;
    private JTable tablaRecuento;
    private ArrayList<Error> errores;
    private ArrayList<Token> tokens;

    /**
     * Contrusctor inicializa las tablas que serviran para hacer los reportes
     *
     * @param tablaErrores
     * @param tablaTokens
     * @param tablaRecuento
     * @param errores
     * @param tokens
     */
    public Reporte(JTable tablaErrores, JTable tablaTokens, JTable tablaRecuento, ArrayList<Error> errores, ArrayList<Token> tokens) {
        this.tablaErrores = tablaErrores;
        this.tablaTokens = tablaTokens;
        this.tablaRecuento = tablaRecuento;
        this.errores = errores;
        this.tokens = tokens;
    }

    /**
     * Este metodo le da un formato a la tabla que sea aplicado, desavilita su
     * edicion
     *
     * @return
     */
    private DefaultTableModel setearModelo() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false; //esto hace que todas las fias no sean eitables
            }
        };
        return modelo;
    }

    public void cargarTablaErrores() {
        DefaultTableModel modelo = setearModelo();
        //anadir todas las columnas necesarias para la tabla
        modelo.addColumn("Cadena de error");
        modelo.addColumn("Fila");
        modelo.addColumn("Columna");
        //exploramos todas las materias primas, y creamos una fila a partir de los atributos del objeto
        for (Error item : errores) {
            Object[] fila = new Object[3];
            fila[0] = item.getCadenaDeError();
            fila[1] = item.getFila();
            fila[2] = item.getColumna();
            modelo.addRow(fila);//anadimos la nueva fila
        }
        tablaErrores.setModel(modelo);//una vez acabado el
    }
    public void cargarTablaTokens(){
        if(errores.isEmpty()){
             DefaultTableModel modelo = setearModelo();
        //anadir todas las columnas necesarias para la tabla
        modelo.addColumn("Tipo de token");
        modelo.addColumn("Lexema");
        modelo.addColumn("Fila");
        modelo.addColumn("Columna");
        //exploramos todas las materias primas, y creamos una fila a partir de los atributos del objeto
        for (Token item : tokens) {
            Object[] fila = new Object[4];
            fila[0] = item.getTipoDeToken();
            fila[1] = item.getLexema();
            fila[2] = item.getFila();
            fila[3] = item.getColumna();
            modelo.addRow(fila);//anadimos la nueva fila
        }
        tablaTokens.setModel(modelo);//una vez acabado el
        }
    }
    public void cargarTablaRecuento(){
        if(errores.isEmpty()){
             DefaultTableModel modelo = setearModelo();
        //anadir todas las columnas necesarias para la tabla
        modelo.addColumn("Lexema");
        modelo.addColumn("Token");
        modelo.addColumn("Veces que aparece");
        //exploramos todas las materias primas, y creamos una fila a partir de los atributos del objeto
        for (Token item : tokens) {
            Object[] fila = new Object[4];
            fila[0] = item.getTipoDeToken();
            fila[1] = item.getLexema();
            fila[2] = item.getFila();
            fila[3] = item.getColumna();
            modelo.addRow(fila);//anadimos la nueva fila
        }
        tablaTokens.setModel(modelo);//una vez acabado el
        }
    }
}
