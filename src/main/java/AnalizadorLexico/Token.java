
package AnalizadorLexico;

public class Token {
    private String tipoDeToken;
    private String lexema;
    private int fila;
    private int columna;

    public Token(String tipoDeToken, String lexema, int fila, int columna) {
        this.tipoDeToken = tipoDeToken;
        this.lexema = lexema;
        this.fila = fila;
        this.columna = columna;
    }

    public String getTipoDeToken() {
        return tipoDeToken;
    }

    public void setTipoDeToken(String tipoDeToken) {
        this.tipoDeToken = tipoDeToken;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
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
