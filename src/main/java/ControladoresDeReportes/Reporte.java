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
        //exploramos todas os errores, y creamos una fila a partir de los atributos del objeto
        for (Error item : errores) {
            Object[] fila = new Object[3];
            fila[0] = item.getCadenaDeError();
            fila[1] = item.getFila();
            fila[2] = item.getColumna();
            modelo.addRow(fila);//anadimos la nueva fila
        }
        tablaErrores.setModel(modelo);//una vez acabado el
    }

    public void cargarTablaTokens() {
        DefaultTableModel modelo = setearModelo();
        //anadir todas las columnas necesarias para la tabla
        modelo.addColumn("Tipo de token");
        modelo.addColumn("Lexema");
        modelo.addColumn("Fila");
        modelo.addColumn("Columna");
        tablaTokens.setModel(modelo);//una vez acabado el
        if (errores.isEmpty()) {
            //exploramos todas los tokens, y creamos una fila a partir de los atributos del objeto
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

    public void cargarTablaRecuento() {
        DefaultTableModel modelo = setearModelo();
        //anadir todas las columnas necesarias para la tabla
        modelo.addColumn("Lexema");
        modelo.addColumn("Token");
        modelo.addColumn("Veces que aparece");
        tablaRecuento.setModel(modelo);
        if (errores.isEmpty()) {
           ArrayList<Recuento> recuentos = hacerRecuentoDeLexemas();
           //exploramos todas los tokens, y creamos una fila a partir de los atributos del objeto
            for (Recuento item : recuentos) {
                Object[] fila = new Object[3];
                fila[0] = item.getLexema();
                fila[1] = item.getToken();
                fila[2] = item.getCantidadQueAparece();
                modelo.addRow(fila);//anadimos la nueva fila
            }
            tablaRecuento.setModel(modelo);//una vez acabado el
        }
    }
    private ArrayList<Recuento> hacerRecuentoDeLexemas(){
        ArrayList<Recuento> recuento = new ArrayList<>();
        boolean banderaLexemaExiste = false;
        for(Token itemToken : tokens){
            banderaLexemaExiste = false;
            for(Recuento itemRecuento : recuento){
                if(itemRecuento.getLexema().equals(itemToken.getLexema())){//vemos si se trata del mismo lexema
                    banderaLexemaExiste = true;
                    int vecesQueAparece = itemRecuento.getCantidadQueAparece();
                    itemRecuento.setCantidadQueAparece(vecesQueAparece + 1);
                }
            }
            if(banderaLexemaExiste == false){
                recuento.add(new Recuento(itemToken.getLexema(),itemToken.getTipoDeToken(), 1));
            }
        }
        return recuento;
    }
}
