
package ControladoresDeReportes;

public class Recuento {
    String lexema;
    String token;
    int cantidadQueAparece;

    public Recuento(String lexema, String token, int cantidadQueAparece) {
        this.lexema = lexema;
        this.token = token;
        this.cantidadQueAparece = cantidadQueAparece;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCantidadQueAparece() {
        return cantidadQueAparece;
    }

    public void setCantidadQueAparece(int cantidadQueAparece) {
        this.cantidadQueAparece = cantidadQueAparece;
    }
    
}
