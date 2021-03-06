package avion;

import customExceptions.SeatAlreadyOccupiedException;

public class Asiento {
    int fila;
    char columna;
    boolean ocupado;
    int DNI;
    String clase;

    public Asiento(int fila, char columna, String clase) {
        this.fila = fila;
        this.columna = columna;
        this.clase = clase;
        this.ocupado = false;
    }

    public int getFila() {
        return fila;
    }

    public char getColumna() {
        return columna;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public String getClase() {
        return clase;
    }

    public String getFilaYColumna(){
        return fila + Character.toString(columna); // 15A

    }

    public void ocupar(int DNI){

        if(this.isOcupado()){
            throw new SeatAlreadyOccupiedException("Asiento ya ocupado");
        }

        this.DNI = DNI;
        ocupado = true;
    }

    public void vaciar(){
        ocupado = false;
        DNI = 0;
    }

    public int getDni() {
        return DNI;
    }
}
