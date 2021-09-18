
package AnalizadorLexico;

public class Error {
    private String cadenaDeError;
    private int fila;
    private int columna;

    public Error(String cadenaDeError, int fila, int columna) {
        this.cadenaDeError = cadenaDeError;
        this.fila = fila;
        this.columna = columna;
    }

    public String getCadenaDeError() {
        return cadenaDeError;
    }

    public void setCadenaDeError(String cadenaDeError) {
        this.cadenaDeError = cadenaDeError;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    
}
